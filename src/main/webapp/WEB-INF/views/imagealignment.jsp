							<spring:bind path="dataset.figure_red">
								<div class="control-group  ${status.error ? 'error' : ''}">
									<label class="control-label" for="imageRed">Image Red</label>
									<div class="controls">
										<form:select id="imageRed" path="dataset.figure_red">
											<option></option>
											<form:options items="${imageChoices}"></form:options>
										</form:select>
										<span class='help-inline'>${status.errorMessage}</span>
									</div>
								</div>
							</spring:bind>


							<spring:bind path="dataset.figure_blue">
								<div class="control-group  ${status.error ? 'error' : ''}">
									<label class="control-label" for="imageBlue">Image Blue</label>
									<div class="controls">
										<form:select id="imageBlue" path="dataset.figure_blue">
											<option></option>
											<form:options items="${imageChoices}"></form:options>
										</form:select>
										<span class='help-inline'>${status.errorMessage}</span>
									</div>
								</div>
							</spring:bind>
							
							

							<spring:bind path="dataset.alignment_field1">
								<div class="control-group  ${status.error ? 'error' : ''}">
									<label class="control-label" for="alignment">Alignment
										Matrix</label>
									<div class="controls controls-row">
										<form:input class="span1" type="text" id="alignmentField1"
											path="dataset.alignment_field1" />
										<form:input class="span1" type="text" id="alignmentField2"
											path="dataset.alignment_field2" />
										<form:input class="span1" type="text" id="alignmentField3"
											path="dataset.alignment_field3" />
									</div>
									<div class="controls controls-row">
										<form:input class="span1" type="text" id="alignmentField4"
											path="dataset.alignment_field4" />
										<form:input class="span1" type="text" id="alignmentField5"
											path="dataset.alignment_field5" />
										<form:input class="span1" type="text" id="alignmentField6"
											path="dataset.alignment_field6" />
									</div>
									<div class="controls controls-row">
										<form:input class="span1" type="text" id="alignmentField7"
											path="dataset.alignment_field7" />
										<form:input class="span1" type="text" id="alignmentField8"
											path="dataset.alignment_field8" />
										<form:input class="span1" type="text" id="alignmentField9"
											path="dataset.alignment_field9" />
									</div>

								</div>

							</spring:bind>