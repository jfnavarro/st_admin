/*
*Copyright © 2012 Spatial Transcriptomics AB
*Read LICENSE for more information about licensing terms
*Contact: Jose Fernandez Navarro <jose.fernandez.navarro@scilifelab.se>
* 
*/

//package com.spatialtranscriptomics.util;
//
//import javax.validation.ConstraintValidator;
//import javax.validation.ConstraintValidatorContext;
//
//import org.springframework.web.multipart.commons.CommonsMultipartFile;
//
///**
// * This class implements a custom ConstraintValidator.
// * It is used in DatasetAddForm.class to validate if a Feature file was uploaded 
// * see also FileSelectedConstraint.class
// * 
// * */
//public class FileSelectedValidator implements
//		ConstraintValidator<FileSelectedConstraint, CommonsMultipartFile> {
//
//	public boolean isValid(CommonsMultipartFile objectValue,
//			ConstraintValidatorContext context) {
//		if (objectValue == null)
//			return true;
//		if (!objectValue.isEmpty()) {
//			return true;
//		} else {
//			return false;
//		}
//	}
//
//	public void initialize(FileSelectedConstraint constraintAnnotation) {
//	}
//
//}
