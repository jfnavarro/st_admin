/*
*Copyright © 2012 Spatial Transcriptomics AB
*Read LICENSE for more information about licensing terms
*Contact: Jose Fernandez Navarro <jose.fernandez.navarro@scilifelab.se>
* 
*/

package com.spatialtranscriptomics.auth;

import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Service;

import com.spatialtranscriptomics.model.Account;

/**
 * This class implements a custom Spring Security AuthenticationProvider. 
 * It does the following:
 * 1) set the passed in credentials to the SecureRestTemplate 
 * 2) make a request to retrieve current user using the SecureRestTemplate. 
 * 3) authenticate: if the response of this request is an "Account" object, the authentication against the API was successful. 
 * 4) authorize: grant the user access if the role in the returned "Account" object is "ROLE_CM" or "ROLE_ADMIN"  
 */

@Service
public class APIAuthenticationProvider implements AuthenticationProvider {

	private static final Logger logger = Logger
			.getLogger(APIAuthenticationProvider.class);

	@Autowired
	CustomOAuth2RestTemplate secureRestTemplate;

	@Autowired
	Properties appConfig;

	public Authentication authenticate(Authentication authentication)
			throws AuthenticationException {
		String name = authentication.getName();
		String password = authentication.getCredentials().toString();

		String role = authenticateAgainstAPI(name, password);
		
		if (role!=null) {
			List<GrantedAuthority> grantedAuths = new ArrayList<GrantedAuthority>();
			grantedAuths.add(new SimpleGrantedAuthority(role));
			Authentication auth = new UsernamePasswordAuthenticationToken(name,
					password, grantedAuths);
			return auth;
		} else {
			throw new BadCredentialsException("Bad Credentials");
		}
	}

	public boolean supports(Class<?> authentication) {
		return authentication.equals(UsernamePasswordAuthenticationToken.class);
	}

	private String authenticateAgainstAPI(String username, String password) {

		try {

			// set the given credentials to the secureRestTemplate
			secureRestTemplate.setResourceCredentials(username, password);

			// authenticate and authorize against API through secureRestTemplate
			String url = appConfig.getProperty("url.account");
			url += "current/user";
			Account account = secureRestTemplate.getForObject(url,
					Account.class);

			// authorize if user has role ROLE_CM or ROLE_ADMIN (at API)
			String role = account.getRole();
			if(role.equals("ROLE_CM") || role.equals("ROLE_ADMIN")){
				return role;
			}
			else{
				return null;
			}
			
			

		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}

	}
}
