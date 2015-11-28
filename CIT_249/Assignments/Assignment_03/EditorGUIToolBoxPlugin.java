/**
	interface EditorGUIToolBoxPlugin
		- Interface used to define a toolbox plugin for the EditorGUI.

	Author: Patrick Hibbs
	E-mail address: phibbs0003@kctcs.edu
	Last changed: 11/26/2015
	Assignment 03
*/

import javafx.stage.Stage;

public interface EditorGUIToolBoxPlugin {

	/*!
		public String getToolboxButtonLabel()

		Returns the text to show in the toolbox
		button for this interface.
	 */
	public String getToolboxButtonLabel();

	/*!
		public boolean hasExtraOptions()

		Whether or not this interface contains
		additional options for the user to set
		that (can) affect the generated source
		code.

		If this returns true, the GUI will expect
		getExtraOptionsStage() to return a valid
		Stage object.
	 */
	public boolean hasExtraOptions();

	/*!
		public Stage getExtraOptionsStage()

		Returns a reference to a Stage that
		contains GUI components that allow the
		user to set the interface's extra options
		that (can) affect the generated source code.

		This is only called if hasExtraOptions()
		returns true, and the user requests it.

		The implication here is that the GUI will
		display the stage and block until the Stage
		closes / hides. Afterward the GUI will expect
		that the options set by the user will be used
		by the interface on the next call to a source
		code generation function.
	*/
	public Stage getExtraOptionsStage();

	/*!
		public boolean hasRequiredArguements()

		Whether or not this interface contains
		additional arguments for the user to set
		that are REQUIRED to generate source
		code.

		If this returns true, the GUI will expect
		getRequiredArguementsStage() to return a
		valid Stage object.
	 */
	public boolean hasRequiredArguements();

	/*!
		public Stage getRequiredArguementsStage()

		Returns a reference to a Stage that
		contains GUI components that allow the
		user to set the interface's arguments
		that are REQUIRED to generate source code.

		This is only called if hasRequiredArguements()
		returns true.

		The implication here is that the GUI will
		display the stage and block until the Stage
		closes / hides. Afterward the GUI will expect
		that the arguments set by the user will be used
		by the interface on the next call to a source
		code generation function.
	*/
	public Stage getRequiredArguementsStage();

	/*!
		public String generateDeclarationCode(final String variableName)

		Returns a string that contains source code for a declaration
		of the object that the interface generates source code for using
		the given variableName and taking into account all user set options
		and arguments.
	 */
	public String generateDeclarationCode(final String variableName);

	/*!
		public String generateInitializationCode(final String variableName)

		Returns a string that contains source code for initializing
		objects that the interface generates source code for using
		the given variableName and taking into account all user set options
		and arguments.
	 */
	public String generateInitializationCode(final String variableName);

	/*!
		public int numberOfFunctionsToCall()

		This function returns the number of functions for the interface's
		object type that the interface can generate calls for.

		If there are no functions that the interface can generate calls for,
		then this function must return zero.
	*/
	public int numberOfFunctionsToCall();

	/*!
		public String[] getFunctionList()

		Returns a human-readable list of functions for the interface's
		object type that the interface can generate calls for.

		If there are no functions that the interface can generate calls for,
		then this function must return null.
	 */
	public String[] getFunctionList();

	/*!
		public boolean functionHasExtraOptions(final int functionListIDX)

		Whether or not this interface contains
		additional options for the user to set
		that (can) affect the generated function
		call. (referenced by the index 
		number for the function in the list returned by 
		getFunctionList().)
	*/
	public boolean functionHasExtraOptions(final int functionListIDX);

	/*!
		public Stage getFunctionExtraOptionsStage(final int functionListIDX)

		Returns a reference to a Stage that
		contains GUI components that allow the
		user to set the interface's extra options
		that (can) affect the generated function call
		for the given function. (referenced by the index 
		number for the function in the list returned by 
		getFunctionList().)

		The implication here is that the GUI will
		display the stage and block until the Stage
		closes / hides. Afterward the GUI will expect
		that the options set by the user will be used
		by the interface on the next call to a source
		code generation function.
	*/
	public Stage getFunctionExtraOptionsStage(final int functionListIDX);

	/*!
		public boolean functionHasRequiredArguements(final int functionListIDX)

		Whether or not the given function (referenced by the index number for the function
		in the list returned by getFunctionList().) has REQUIRED arguments that
		must be defined by the user to generate a call to the function.
	*/
	public boolean functionHasRequiredArguements(final int functionListIDX);

	/*!
		public Stage getFunctionRequiredArguementsStage(final int functionListIDX)

		Returns a reference to a Stage that
		contains GUI components that allow the
		user to set the interface's arguments
		that are REQUIRED to generate a call to the given function.

		The implication here is that the GUI will
		display the stage and block until the Stage
		closes / hides. Afterward the GUI will expect
		that the arguments set by the user will be used
		by the interface on the next call to a source
		code generation function.
	*/
	public Stage getFunctionRequiredArguementsStage(final int functionListIDX);

	/*!
		public String generateFunctionCall(final int functionListIDX, final String variableName)

		Returns a string that contains the generated source code for calling
		the given function (referenced by the index number for the function
		in the list returned by getFunctionList().) with the given variableName
		and taking into account all user set options and arguments.
	*/
	public String generateFunctionCall(final int functionListIDX, final String variableName);

}	/* End of EditorGUIToolBoxPlugin. */
