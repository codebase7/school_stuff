/**
	class EditorGUI_Toolbox_FileWriter_Plugin
		- class used to define a toolbox plugin for the EditorGUI
			that creates code for FileWriter objects from Java.
		- Also shows how to use the EditorGUIToolBoxPlugin interface.
			(Albit very simplistically.)

	Author: Patrick Hibbs
	E-mail address: phibbs0003@kctcs.edu
	Last changed: 11/27/2015
	Assignment 03
*/

import javafx.stage.Stage;

public class EditorGUI_Toolbox_FileWriter_Plugin implements EditorGUIToolBoxPlugin {
	
	private final static String LABEL = "FileWriter";
	private final static String SEMICOLON_TXT = ";";
	private final static String DECLARATION_CODE_PART_1 = "FileWriter ";
	private final static String DECLARATION_CODE_PART_2 = SEMICOLON_TXT;
	private final static String INIT_CODE = " = new FileWriter();";

	@Override
	public String getToolboxButtonLabel() {
		return this.LABEL;
	}

	@Override
	public boolean hasExtraOptions() {
		return false;
	}

	@Override
	public Stage getExtraOptionsStage() {
		return null;
	}

	@Override
	public boolean hasRequiredArguements() {
		return false;
	}

	@Override
	public Stage getRequiredArguementsStage() {
		return null;
	}

	@Override
	public String generateDeclarationCode(final String variableName) {
		return (new String(this.DECLARATION_CODE_PART_1 + variableName + DECLARATION_CODE_PART_2));
	}

	@Override
	public String generateInitializationCode(final String variableName) {
		return (new String(variableName + this.INIT_CODE));
	}

	@Override
	public int numberOfFunctionsToCall() {
		return 0;
	}

	@Override
	public String[] getFunctionList() {
		return null;
	}

	@Override
	public boolean functionHasExtraOptions(final int functionListIDX) {
		return false;
	}

	@Override
	public Stage getFunctionExtraOptionsStage(final int functionListIDX) {
		return null;
	}

	@Override
	public boolean functionHasRequiredArguements(final int functionListIDX) {
		return false;
	}

	@Override
	public Stage getFunctionRequiredArguementsStage(final int functionListIDX) {
		return null;
	}

	@Override
	public String generateFunctionCall(final int functionListIDX, final String variableName) {
		return "";
	}

}	/* End of EditorGUI_Toolbox_FileWriter_Plugin. */
