/*
*Copyright © 2012 Spatial Transcriptomics AB
*Read LICENSE for more information about licensing terms
*Contact: Jose Fernandez Navarro <jose.fernandez.navarro@scilifelab.se>
* 
*/

//package com.spatialtranscriptomics.util;
//
//
//import static java.lang.annotation.ElementType.*;
//import static java.lang.annotation.RetentionPolicy.*;
//
//import java.lang.annotation.Documented;
//import java.lang.annotation.Retention;
//import java.lang.annotation.Target;
//
//import javax.validation.Constraint;
//import javax.validation.Payload;
//
///**
// * This class implements a custom validation constraint
// * It is used in DatasetAddForm.class to validate if a Feature file is uploaded.
// * see also FileSelectedValidator.class
// * 
// * */
//@Target({ METHOD, FIELD, ANNOTATION_TYPE })
//@Retention(RUNTIME)
//@Constraint(validatedBy = FileSelectedValidator.class)
//@Documented
//public @interface FileSelectedConstraint {
//	String message() default "Select a feature file";
//
//	Class<?>[] groups() default {};
//
//	Class<? extends Payload>[] payload() default {};
//}
