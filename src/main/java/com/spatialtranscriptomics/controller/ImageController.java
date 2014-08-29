/*
*Copyright © 2012 Spatial Transcriptomics AB
*Read LICENSE for more information about licensing terms
*Contact: Jose Fernandez Navarro <jose.fernandez.navarro@scilifelab.se>
* 
*/

package com.spatialtranscriptomics.controller;

import com.spatialtranscriptomics.form.ImageForm;
import com.spatialtranscriptomics.model.ImageMetadata;
import com.spatialtranscriptomics.model.JPEGWrapper;
import com.spatialtranscriptomics.serviceImpl.ImageServiceImpl;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import javax.validation.Valid;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

/**
 * This class is Spring MVC controller class for the URL "/image". It implements the methods available at this URL and returns views (.jsp pages) with models or image payload (BufferedImage).
 */

@Controller
@RequestMapping("/image")
public class ImageController {

	@SuppressWarnings("unused")
	private static final Logger logger = Logger
			.getLogger(ImageController.class);

	@Autowired
	ImageServiceImpl imageService;

	// list names
	@RequestMapping(method = RequestMethod.GET)
	public @ResponseBody
	ModelAndView listMetadata() {
		List<ImageMetadata> imageMetadata = imageService.list();
		return new ModelAndView("imagelist", "imagemetadata", imageMetadata);
	}

	// get decompressed image
	@RequestMapping(value = "{id:.+}", method = RequestMethod.GET)
	public @ResponseBody
	BufferedImage get(@PathVariable String id) {
		BufferedImage img = imageService.find(id);
		return img;
	}
        
        
        // get compressed image
	@RequestMapping(value = "/compressed/{id:.+}", method = RequestMethod.GET, produces = "image/jpeg")
	public @ResponseBody
	byte[] getCompressed(@PathVariable String id) {
            JPEGWrapper img = imageService.findCompressedAsJSON(id);
            byte[] imgdata = img.getImage();
            //System.out.println(imgdata.length);
            return imgdata;
	}

	// add
	@RequestMapping(value = "/add", method = RequestMethod.GET)
	public ModelAndView add() {
		return new ModelAndView("imageadd", "imageform", new ImageForm());
	}
        
        // add
	@RequestMapping(value = "/compressed/add", method = RequestMethod.GET)
	public ModelAndView addCompressed() {
		return new ModelAndView("imageadd", "imageform", new ImageForm());
	}

	
	// add submit
	@RequestMapping(value = "/submitadd", method = RequestMethod.POST)
	public// @ResponseBody
	ModelAndView submitAdd(@ModelAttribute("imageform") @Valid ImageForm imageForm, BindingResult result) {
		if (result.hasErrors()) {
			ModelAndView model = new ModelAndView("imageadd", "imageform", imageForm);
			model.addObject("errors", result.getAllErrors());
			return model;
		}

		// Check if image already exists
		List<ImageMetadata> imd = imageService.list();
		List<String> imageNames = new ArrayList<String>();
		for (ImageMetadata im : imd) {
			imageNames.add(im.getFilename());
		}
		if (imageNames.contains(imageForm.getFileName())) {
			ModelAndView model = new ModelAndView("imagelist", "imagemetadata", imd);
			model.addObject("err", "An image with this name already exists. Choose another name or delete existing image.");
			return model;
		}
		else {
                    try {
			imageService.addFromFile(imageForm.getImageFile());
			ModelAndView model = new ModelAndView("imagelist", "imagemetadata",	imageService.list());
			model.addObject("msg", "Image imported.");
			return model;
                    } catch (IOException ex) {
                        ModelAndView model = new ModelAndView("imagelist", "imagemetadata", imd);
			model.addObject("err", "Error importing image. Format seems invalid.");
			return model;
                    }
		}
	}
        
        // add submit
	@RequestMapping(value = "/compressed/submitadd", method = RequestMethod.POST)
	public// @ResponseBody
	ModelAndView submitAddCompressed(@ModelAttribute("imageform") @Valid ImageForm imageForm, BindingResult result) {
		if (result.hasErrors()) {
			ModelAndView model = new ModelAndView("imageadd", "imageform", imageForm);
			model.addObject("errors", result.getAllErrors());
			return model;
		}

		// Check if image already exists
		List<ImageMetadata> imd = imageService.list();
		List<String> imageNames = new ArrayList<String>();
		for (ImageMetadata im : imd) {
			imageNames.add(im.getFilename());
		}
		if (imageNames.contains(imageForm.getFileName())) {
			ModelAndView model = new ModelAndView("imagelist", "imagemetadata", imd);
			model.addObject("err", "An image with this name already exists. Choose another name or delete existing image.");
			return model;
		}
		else {
                    try {
			imageService.addFromFileCompressedAsJSON(imageForm.getImageFile());
			ModelAndView model = new ModelAndView("imagelist", "imagemetadata", imageService.list());
			model.addObject("msg", "Image imported.");
			return model;
                    } catch (IOException ex) {
                        ModelAndView model = new ModelAndView("imagelist", "imagemetadata", imd);
			model.addObject("err", "Error importing image. Format seems invalid.");
			return model;
                    }
		}
	}

	// delete
	@RequestMapping(value = "/{id:.+}/delete", method = RequestMethod.GET)
	public ModelAndView delete(@PathVariable String id) {
		imageService.delete(id);
		List<ImageMetadata> imageMetadata = imageService.list();
		ModelAndView success = new ModelAndView("imagelist", "imagemetadata", imageMetadata);
		success.addObject("msg", "Image deleted.");
		return success;
	}

}
