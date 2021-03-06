package com.st.auth;

import com.st.component.StaticContextAccessor;
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

import com.st.model.Account;
import org.springframework.web.client.RestClientException;

/**
 * This class implements a custom Spring Security AuthenticationProvider. It
 * does the following: 1) set the passed in credentials to the
 * SecureRestTemplate 2) make a request to retrieve current user using the
 * SecureRestTemplate. 3) authenticate: if the response of this request is an
 * "Account" object, the authentication against the API was successful. 4)
 * authorize: grant the user access if the role in the returned "Account" object
 * is "ROLE_CM" or "ROLE_ADMIN"
 */
@Service
public class APIAuthenticationProvider implements AuthenticationProvider {

    @SuppressWarnings("unused")
    private static final Logger logger = Logger
            .getLogger(APIAuthenticationProvider.class);

    @Autowired
    CustomOAuth2RestTemplate secureRestTemplate;

    @Autowired
    Properties appConfig;

    /**
     * Returns a token for the autorization.
     *
     * @param authentication the credentials.
     * @return the token.
     * @throws AuthenticationException a bad credentials object, should
     * authentication fail.
     */
    @Override
    public Authentication authenticate(Authentication authentication)
            throws AuthenticationException {
        String username = null;
        String password = null;
        String role = null;
        
        try {
            username = authentication.getName();
            password = authentication.getCredentials().toString();
            role = authenticateAgainstAPI(username, password);
        } catch (Exception ex) {
            String message = "Failed to autenticate " + username + " due to " + ex.getMessage();
            logger.info(message);
            throw new BadCredentialsException(message);
        }

        if (role != null) {
            logger.info("Authenticated account " + username + " as having role " + role);
            List<GrantedAuthority> grantedAuths = new ArrayList<>();
            grantedAuths.add(new SimpleGrantedAuthority(role));
            Authentication auth = new UsernamePasswordAuthenticationToken(username, password, grantedAuths);
            return auth;
        } else {
            String message = "Failed to autenticate account or role of " + username;
            logger.info(message);
            throw new BadCredentialsException(message);
        }
    }

    /**
     * Verfies the authentication object is of the right type.
     *
     * @param authentication authentication.
     * @return true if correct type.
     */
    @Override
    public boolean supports(Class<?> authentication) {
        return authentication.equals(UsernamePasswordAuthenticationToken.class);
    }

    /**
     * Authenticates a user against the API by
     * 1) Checking that the account exists.
     * 2) Verifying that the role of the user is acceptable.
     *
     * @param username username.
     * @param password password.
     * @return the user role ID, or null if authentication fails.
     */
    private String authenticateAgainstAPI(String username, String password) {
        try {
            // set the given credentials to the secureRestTemplate
            secureRestTemplate.setResourceCredentials(username, password);

            // authenticate and authorize against API through secureRestTemplate
            String url = appConfig.getProperty("url.account");
            url += "current/user";
            Account account = secureRestTemplate.getForObject(url, Account.class);

            // authorize if user has role ROLE_CM or ROLE_ADMIN (at API)
            String role = account.getRole();
            if (role.equals("ROLE_CM") || role.equals("ROLE_ADMIN")) {
                // Sets the current user for simpler global access.
                StaticContextAccessor.setCurrentUser(account);
                return role;
            } else {
                StaticContextAccessor.setCurrentUser(null);
                return null;
            }

        } catch (RestClientException e) {
            logger.error("Failed to authenticate user " + username + " with secure rest remplate " + e.getMessage());
            return null;
        }

    }
}
