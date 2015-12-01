/**
	Class EditorGUI
		- A small source code editor in lieu of CrimsonEditor. 
	Author: Patrick Hibbs
	E-mail address: phibbs0003@kctcs.edu
	Last changed: 12/1/2015
	Assignment 03
*/

import java.util.Scanner;
import javafx.application.Application;	
import javafx.event.ActionEvent;	
import javafx.event.EventHandler;	
import javafx.stage.Stage;			
import javafx.scene.Scene;			
import javafx.geometry.Insets;			
import javafx.scene.control.Button;	
import javafx.stage.Modality;		
import javafx.scene.control.TextField;		
import javafx.scene.control.TextArea;	
import javafx.scene.control.Label;			
import javafx.scene.control.RadioButton;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.layout.GridPane;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuItem;
import javafx.scene.control.MenuBar;
import javafx.scene.input.KeyCombination;
import java.net.URL;						/* Url string. */
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import javafx.stage.FileChooser;
import javafx.scene.control.SplitPane;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TabPane;		/* Contains the Tabs. */
import javafx.scene.control.Tab;			/* Contains the EditorPanes. */
import javafx.scene.Node;
import java.util.Vector;
import java.lang.ProcessBuilder;			/* Create new processes. */
import java.lang.ClassLoader;				/* Abstract dynamic loader. */
import java.net.URLClassLoader;				/* URL Class loader. */
import javafx.stage.Window;
import javafx.scene.Parent;
import javafx.collections.ObservableList;
import javafx.scene.layout.Region;
import java.lang.Exception;				/* Exceptions for bad programmers. */

public class EditorGUI extends Application {
	/* Init vars. */
	private final static String UNTITLED_FILE_TXT = "Untitled";
	private final static String TEXT_FILE_EXTENSION_TXT = ".txt";
	private final static String JAVA_FILE_EXTENSION_TXT = ".java";
	private final static String MAIN_WINDOW_TITLE_TXT = "EditorGUI";
	private final static String PLUGIN_FUNCTION_SELECTION_WINDOW_TITLE_TXT = "Plugin Function Selection";
	private final static String PLUGIN_FUNCTION_SELECTION_WINDOW_INSTRUCTIONS_LBL = "Please select a function from the list below to generate code for:";
	private final static String OK_TXT = "OK";
	private final static String CANCEL_TXT = "Cancel";
	private final static String TOOLBOX_GENERATE_CODE_FOR_LBL = "Generate code for: ";
	private final static String TOOLBOX_DECLARATION_CHECKBOX_LBL = "Declaration";
	private final static String TOOLBOX_INITIALIZATION_CHECKBOX_LBL = "Initialization";
	private final static String TOOLBOX_REQUIREDARGS_CHECKBOX_LBL = "Required Arguements";
	private final static String TOOLBOX_EXTRAARGS_CHECKBOX_LBL = "Extra Arguements";
	private final static String TOOLBOX_FUNCTIONCALL_CHECKBOX_LBL = "Function Call";
	private final static String PREVIOUS_SESSION_FILENAME = "openTabs.txt";
	private final static String VARIABLE_NAME_WINDOW_TITLE_TXT = "Variable name entry";
	private final static String VARIABLE_NAME_WINDOW_TXT = "Please input a variable name for the object:";
	private final static String NEW_CLASS_NAME_WINDOW_TITLE_TXT = "New class name entry";
	private final static String IMPORT_CLASS_NAME_WINDOW_TITLE_TXT = "Import class name entry:";
	private final static String IMPORT_PACKAGE_NAME_WINDOW_TITLE_TXT = "Import package name entry:";
	private final static String PACKAGE_STATEMENT_WINDOW_TITLE_TXT = "Package Statement entry:";
	private final static String CLASS_NAME_WINDOW_TXT = "Please input a class name:";
	private final static String PACKAGE_NAME_WINDOW_TXT = "Please input a package name:";
	private final static String CLASS_DECLARATION_PART_1_TXT = "public class ";
	private final static String CLASS_DECLARATION_PART_2_TXT = " { }";
	private final static String IMPORT_STATEMENT_TXT = "import ";
	private final static String PACKAGE_STATEMENT_TXT = "package ";
	private final static String SEMICOLON_TXT = ";";
	private final static String ERROR_MESSAGE_WINDOW_TITLE_TXT = "ERROR";
	private final static String ERROR_MESSAGE_BAD_CLASS_NAME_TXT = "Invalid class name. Please try again.";
	private final static String ERROR_MESSAGE_BAD_PACKAGE_NAME_TXT = "Invalid package name. Please try again.";
	private final static String OPEN_FILE_TITLE_TXT = "Open File";
	private final static String SAVE_FILE_TITLE_TXT = "Save File";
	private final static String TOOLBOX_PLUGIN_CLASS_REGEX = "*";
	private final static String PLUGIN_REFERENCE_PATH = "plugins";
	private final static String CLASS_EXTENSION_TXT = ".class";
	private final static String MSDOS_SHELL_CMD_TXT = "cmd";
	private final static String MSDOS_SHELL_ARG_1_TXT = "/c";
	private final static String MSDOS_SHELL_ARG_2_TXT = "start";
	private final static String ABOUT_WINDOW_TITLE_TXT = ("About " + MAIN_WINDOW_TITLE_TXT);
	private final static String ABOUT_WINDOW_TXT = ("Class EditorGUI\n\t - A small source code editor in lieu of CrimsonEditor.\n" +
													"Author: Patrick Hibbs\nE-mail address: phibbs0003@kctcs.edu\n" +
													"Last changed: 11/27/2015\nAssignment 03");
	private final static int TOOLBOX_BUTTON_INSETS = 10;
	private final static int TOOLBOX_MAX_COLUMNS = 5;
	private Stage guiStage;
	private Scene guiScene;
	private VBox guiVBox;				/* GUI Root node. */
	private MenuBar menuBar;			/* Menu bar. */
	private MenuHandler menuHandler;
	private Menu fileMenu;
	private Menu classMenu;
	private Menu packageMenu;
	private Menu toolsMenu;
	private Menu helpMenu;
	private MenuItem fileOpenMenuItem;
	private MenuItem fileSaveMenuItem;
	private MenuItem fileExitMenuItem;
	private MenuItem classNewMenuItem;
	private MenuItem classImportMenuItem;
	private MenuItem packageImportMenuItem;
	private MenuItem packageAddStatementMenuItem;
	private MenuItem toolsDOSShellMenuItem;
	private MenuItem helpAboutMenuItem;
	private SplitPane guiSplitPane;		/* Contains editors and toolbox. */
	private TabPane guiTabPane;			/* Contains the editor tabs. */
	private ScrollPane toolboxSP;
	private GridPane toolboxGP;
	private Label toolboxGenerateCodeForLBL;
	private CheckBox toolboxDeclarationCheckbox;
	private CheckBox toolboxInitializationCheckbox;
	private CheckBox toolboxRequiredArgsCheckbox;
	private CheckBox toolboxExtraArgsCheckbox;
	private CheckBox toolboxFunctionCallCheckbox;
	private HBox toolboxUserOptionsHBox;
	private VBox toolboxVBox;
	private ButtonHandler buttonHandler;
	private Vector<Class> loadedToolboxPluginClasses;
	private Vector<EditorGUIToolBoxPlugin> loadedToolboxPlugins;
	private Button guiButtons[];
	private final static boolean DISABLE_DEBUG = true;	/* Whether or not to show all of the fun messages. */

	/*!
		private void CreateNewEditor()

		Creates a new editor pane and inserts it into a new tab in the GUI.
	*/
	private void CreateNewEditor(File newFile, final String untitledTabName, final boolean changeToNewTab) {
		/* Init vars. */
		boolean done = false;
		Tab newTab = null;
		Tab tabFromList = null;
		EditorPane newEditor = null;
		Node nodeFromTab = null;

		/* Check for valid gui. */
		if (this.guiTabPane != null) {
			/* Create the editor. */
			newEditor = new EditorPane();

			/* Check for a file to open. */
			if (newFile != null) {
				newEditor.openFile(newFile);
				newTab = new Tab(newFile.getName());
			}
			else {
				if (untitledTabName != null) {
					newTab = new Tab(untitledTabName);
				}
				else {
					newTab = new Tab(UNTITLED_FILE_TXT);
				}
			}

			/* Check for a valid new tab object. */
			if (newTab != null) {
				/* Set the editorpane into the tab. */
				newTab.setContent(newEditor);

				/* Begin gui parse loop. (For opened files ONLY!) */
				if (newFile != null) {
					for (int x = 0; ((x < (this.guiTabPane.getTabs()).size()) && (!done)); x++) {
						/* Check and see if the given tab is an untitled empty file. */
						tabFromList = (this.guiTabPane.getTabs()).get(x);
						if ((tabFromList.getText()).equals(this.UNTITLED_FILE_TXT)) {
							/* Get the node from the tab. */
							nodeFromTab = tabFromList.getContent();
							if ((nodeFromTab != null) && (nodeFromTab instanceof EditorPane) &&
							(((EditorPane)(nodeFromTab)).isEmpty())) {
								/* Add the tab to the gui tab pane. */
								(this.guiTabPane.getTabs()).set(x, newTab);

								/* Switch to the new tab if needed. */
								if (changeToNewTab) {
									(this.guiTabPane.getSelectionModel()).select(x);
								}

								/* Done. */
								done = true;
							}
						}
					}
				}

				/* Check and see if we added the tab. */
				if (!done) {
					/* Add the tab at the end. */
					(this.guiTabPane.getTabs()).add(newTab);

					/* Switch to the new tab if needed. */
					if (changeToNewTab) {
						(this.guiTabPane.getSelectionModel()).selectLast();
					}

					/* Done. */
					done = true;
				}
			}
		}

		/* Exit function. */
		return;
	}

	private Vector<File> CreateFileListing(File directoryPath) {
		/* Init vars. */
		Vector<File> ret = null;
		Vector<File> retFromRecursion = null;
		File[] tempList = null;

		/* Check for valid path. */
		if (directoryPath != null) {
			/* Begin try block. */
			try {
				/* Allocate our results vector. */
				ret = new Vector<File>();

				/* Get the directory listing. */
				tempList = directoryPath.listFiles();
				if (tempList != null) {
					/* Check for subdirectries. */
					for (int x = 0; (x < tempList.length); x++) {
						if (tempList[x].isDirectory()) {
							/* Get subdirectory listing via recursion. */
							retFromRecursion = CreateFileListing(tempList[x]);
							if (retFromRecursion != null) {
								/* Copy the results from the recursion into our result list. */
								for (int y = 0; (y < retFromRecursion.size()); y++) {
									ret.add(((x > 0) ? (x - 1) : (0)), (retFromRecursion.elementAt(y)));
								}
								retFromRecursion = null;
							}
						}
						else if (tempList[x].isFile()) {
							ret.add(tempList[x]);
						}
					}
				}
			}
			catch (Exception e) {
				/* Exception thrown. */
				System.out.println("\nEXCEPTION: CreateFileListing(): " + e.getMessage());
				ret = null;
			}
		}

		/* Exit function. */
		return ret;
	}

	private void CreateToolBox() {
		/* Check for valid split pane. */
		if (this.guiSplitPane != null) {
			/* Init vars. */
			boolean pluginAlreadyLoaded = false;
			File cwd = null;
			File pluginDir = null;
			Vector<File> fileList = null;
			Vector<File> pluginDirFilesList = null;
			URL pluginDirUrl = null;
			URL baseDirUrl = null;
			URL[] urls = null;
			ClassLoader classLoader = null;
			Class tempClass = null;
			Object tempObject = null;
			File tempFile = null;
			String fileName = null;
			String toolName = null;
			Button tempButton = null;
			EditorGUIToolBoxPlugin tempPlugin = null;
			Vector<Button> tempButtonVector = null;

			/* Create the new ButtonHandler. */
			this.buttonHandler = new ButtonHandler();

			/* Create the grid pane. */
			this.toolboxGP = new GridPane();

			/* Create the ScrollPane. */
			this.toolboxSP = new ScrollPane();
			this.toolboxSP.setContent(this.toolboxGP);

			/* Create the label for the tool box checkboxes. */
			this.toolboxGenerateCodeForLBL = new Label(TOOLBOX_GENERATE_CODE_FOR_LBL);

			/* Create the label for the toolbox user options. */
			this.toolboxGenerateCodeForLBL = new Label(TOOLBOX_GENERATE_CODE_FOR_LBL);

			/* Create CheckBoxes for user options. */
			this.toolboxDeclarationCheckbox = new CheckBox(TOOLBOX_DECLARATION_CHECKBOX_LBL);
			this.toolboxInitializationCheckbox = new CheckBox(TOOLBOX_INITIALIZATION_CHECKBOX_LBL);
			this.toolboxRequiredArgsCheckbox = new CheckBox(TOOLBOX_REQUIREDARGS_CHECKBOX_LBL);
			this.toolboxExtraArgsCheckbox = new CheckBox(TOOLBOX_EXTRAARGS_CHECKBOX_LBL);
			this.toolboxFunctionCallCheckbox = new CheckBox(TOOLBOX_FUNCTIONCALL_CHECKBOX_LBL);
			
			/* Set minwidth on the checkboxes. */
			this.toolboxDeclarationCheckbox.setMinWidth(Region.USE_PREF_SIZE);
			this.toolboxInitializationCheckbox.setMinWidth(Region.USE_PREF_SIZE);
			this.toolboxRequiredArgsCheckbox.setMinWidth(Region.USE_PREF_SIZE);
			this.toolboxExtraArgsCheckbox.setMinWidth(Region.USE_PREF_SIZE);
			this.toolboxFunctionCallCheckbox.setMinWidth(Region.USE_PREF_SIZE);

			/* Set action handler for the important checkboxes. */
			this.toolboxDeclarationCheckbox.setOnAction(this.buttonHandler);
			this.toolboxInitializationCheckbox.setOnAction(this.buttonHandler);
			this.toolboxFunctionCallCheckbox.setOnAction(this.buttonHandler);

			/* Create the HBox for the user options. */
			this.toolboxUserOptionsHBox = new HBox();
			this.toolboxUserOptionsHBox.getChildren().addAll(	this.toolboxDeclarationCheckbox,
																this.toolboxInitializationCheckbox,
																this.toolboxFunctionCallCheckbox,
																this.toolboxRequiredArgsCheckbox,
																this.toolboxExtraArgsCheckbox);
			this.toolboxUserOptionsHBox.setMargin(this.toolboxDeclarationCheckbox, new Insets(10));
			this.toolboxUserOptionsHBox.setMargin(this.toolboxInitializationCheckbox, new Insets(10));
			this.toolboxUserOptionsHBox.setMargin(this.toolboxRequiredArgsCheckbox, new Insets(10));
			this.toolboxUserOptionsHBox.setMargin(this.toolboxExtraArgsCheckbox, new Insets(10));
			this.toolboxUserOptionsHBox.setMargin(this.toolboxFunctionCallCheckbox, new Insets(10));

			/* Create the VBox. */
			this.toolboxVBox = new VBox();
			this.toolboxVBox.getChildren().addAll(	this.toolboxGenerateCodeForLBL,
													this.toolboxUserOptionsHBox,
													this.toolboxSP);
			this.guiSplitPane.getItems().addAll(this.toolboxVBox);

			/* Purge the loaded classes vector. */
			this.loadedToolboxPluginClasses = new Vector<Class>();

			/* Purge the loaded plugins vector. */
			this.loadedToolboxPlugins = new Vector<EditorGUIToolBoxPlugin>();

			/* Create new Button vector. */ 
			tempButtonVector = new Vector<Button>();

			/* Open the current directory. */
			cwd = new File(new String(((System.getProperty("user.dir")) + "/")));
			pluginDir = new File(new String(((System.getProperty("user.dir")) + "/" + PLUGIN_REFERENCE_PATH + "/")));
			if (pluginDir != null) {
				/* Begin try block. */
				try {
					/* Create files list. */
					fileList = this.CreateFileListing(cwd);
					if (fileList != null) {
						pluginDirFilesList = this.CreateFileListing(pluginDir);
						if (pluginDirFilesList != null) {
							for (int y = 0; (y < pluginDirFilesList.size()); y++) {
								fileList.add((pluginDirFilesList.elementAt(y)));
							}
						}

						/* Convert file to a URL for the UrlClassLoader. */
						baseDirUrl = (cwd.toURI()).toURL();
						pluginDirUrl = (pluginDir.toURI()).toURL();
						urls = new URL[]{baseDirUrl, pluginDirUrl};

						/* Check for a null url list. */
						if (urls != null) {
							/* Create the URLClassLoader and set the url list as it's source to search for classes in. */
							classLoader = new URLClassLoader(urls);
							if (classLoader != null) {

								/* Begin url parsing loop. */
								for (int x = 0; (x < fileList.size()); x++) {

									/* Get the file name. */
									tempFile = fileList.elementAt(x);
									if (tempFile != null) {
										fileName = tempFile.getName();
										if ((fileName != null) && (fileName.length() > 0)) {

											/* Remove the class extension. */
											if (fileName.contains(CLASS_EXTENSION_TXT)) {
												fileName = fileName.substring(0, (fileName.length() - CLASS_EXTENSION_TXT.length()));
												if (fileName.length() > 0) {

													/* If we found a valid class file, load it into memory. */
													tempClass = classLoader.loadClass(fileName);
													if (tempClass != null) {

														/* Begin try block for class instancing. */
														try {
															/* Create an object of the class. */
															tempObject = tempClass.newInstance();
															if (tempObject != null) {

																/* Check and see if the class has our interfaces. */
																if (EditorGUIToolBoxPlugin.class.isInstance(tempObject)) {
																	/* Get the tool name. */
																	toolName = ((EditorGUIToolBoxPlugin)tempObject).getToolboxButtonLabel();
																	if ((toolName != null) && (toolName.length() > 0)) {

																		/* Check and see if the class has already been loaded. */
																		pluginAlreadyLoaded = false;
																		for (int a = 0; ((a < this.loadedToolboxPlugins.size()) &&
																		(!pluginAlreadyLoaded)); a++) {
																			if (((this.loadedToolboxPlugins.elementAt(a)).getToolboxButtonLabel()).equals(toolName)) {
																				/* The plugin has already been loaded. */
																				pluginAlreadyLoaded = true;
																			}
																		}

																		/* Only add the class to the list if it has not been loaded already. */
																		if (!pluginAlreadyLoaded) {
																			/* Add the class to our loaded classes list. */
																			this.loadedToolboxPluginClasses.add(tempClass);

																			/* Add the loaded object to our loaded object list. */
																			this.loadedToolboxPlugins.add(((EditorGUIToolBoxPlugin)(tempObject)));
																		}

																		/* Reset tool name. */
																		toolName = null;
																	}
																}
															}
														}
														catch (Exception e) {
															/* Class default constructor threw an exception. */
															if (!DISABLE_DEBUG) {
																System.out.println("\nCreateComponentList(): EXCEPTION: Class <" + fileName +
																"> threw an exception from it's default constructor: " + e.getMessage() +
																" while attempting to determine it's interface list. Probably harmless, especially if the class " +
																"lacks a defualt constructor.");
															}
														}

														/* Reset the vars. */
														fileName = null;
														tempObject = null;
														tempClass = null;
													}
													else {
														/* Unable to load class file. */
														System.out.println("\nCreateComponentList(): Unable to load <" + fileName + "> as a class file.");
													}
												}
											}
										}
									}
								}

								/* Print number of loaded plugins to console. */
								System.out.println("\nCreateComponentList(): " +
								((this.loadedToolboxPluginClasses.size() > 0) ?
									("Loaded " + this.loadedToolboxPluginClasses.size() + " plugin" +
									((this.loadedToolboxPluginClasses.size() > 1) ? ("s.") : ("."))) :
								("No plugins loaded.")));

								/* Check and see if the class loader loaded anything. */
								if (this.loadedToolboxPlugins.size() > 0) {
									for (int x = 0; (x < this.loadedToolboxPlugins.size()); x++) {
										/* Get the toolbox plugin's tool name. */
										toolName = (this.loadedToolboxPlugins.elementAt(x)).getToolboxButtonLabel();
										if ((toolName != null) && (toolName.length() > 0)) {
											/* Create a button for the toolbox plugin in the gui. */
											tempButton = new Button(toolName);
											tempButton.setOnAction(this.buttonHandler);

											/* Toolbox buttons are disabled until the user enables at least one of:
												toolboxDeclarationCheckbox, toolboxInitializationCheckbox, or
												toolboxFunctionCallCheckbox.
											*/
											tempButton.setDisable(true);

											/* Copy the button into the button vector. */
											tempButtonVector.add(tempButton);
										}
									}

									/* Check for buttons in the vector. */
									if (tempButtonVector.size() > 0) {
										/* Add all of the buttons to the grid pane. */
										for (int x = 0; (x < tempButtonVector.size());) {
											for (int y = 0; ((x < tempButtonVector.size()) && (y < this.TOOLBOX_MAX_COLUMNS)); y++,x++) {
												this.toolboxGP.addColumn(y, tempButtonVector.elementAt(x));
												this.toolboxGP.setMargin(tempButtonVector.elementAt(x), (new Insets(this.TOOLBOX_BUTTON_INSETS)));
											}
										}
									}

									/* Create the button array from the button vector.

										I personally don't use this array. But someone
										who extends the class might, so I'll make it here.

										(I don't use it because the button pressed is determined
										by comparison of the value of it's label text (After the
										action source object is cast back to a button.) Vs.
										the tool name of each plugin. (Hence why we check for a plugin 
										with the same name as an already loaded plugin during the
										loading sequence.)

										Each tool has a plugin so the original need to keep track of
										the buttons to determine what code to add is no longer required.
										The plugin handles the code generation, and the buttons just act
										as triggers for the plugin code.)

										Note using a vector was easier to do than attempt to do
										reallocations of the array with each button made,
										as we don't know how many buttons are needed until the
										plugins are loaded.
									*/
									this.guiButtons = new Button[1];	/* Prevent NULL pointer exception.
																		(toArray() does not like null array pointers.)
																		*/
									tempButtonVector.toArray(this.guiButtons);
								}
							}
							else {
								/* Could not create class loader. */
								System.out.println("\nCreateComponentList(): Unable to create URLClassLoader.");
							}
						}
						else {
							System.out.println("\nCreateComponentList(): Unable to create urls list.");
						}
					}
					else {
						System.out.println("\nCreateComponentList(): Could not create directory listing.");
					}
				}
				catch (Exception e) {
					System.out.println("\nCreateComponentList(): EXCEPTION: " + e.toString());
				}
			}
		}

		/* Exit function. */
		return;
	}
	
	private void CreateGUIObjects() {
		/* Create the menu bar. */
		this.menuBar = new MenuBar();

		/* Create menu listener. */
		this.menuHandler = new MenuHandler();

		/* Create the file menu. */
		this.fileMenu = new Menu("_File");
		this.fileOpenMenuItem = new MenuItem("_Open file");
		this.fileSaveMenuItem = new MenuItem("_Save file");
		this.fileExitMenuItem = new MenuItem("Exit program");

		/* Set shortcut keys. */
		this.fileMenu.setAccelerator(KeyCombination.keyCombination("SHORTCUT+F"));
		this.fileOpenMenuItem.setAccelerator(KeyCombination.keyCombination("SHORTCUT+O"));
		this.fileSaveMenuItem.setAccelerator(KeyCombination.keyCombination("SHORTCUT+S"));
		this.fileExitMenuItem.setAccelerator(KeyCombination.keyCombination("ALT+F4"));

		/* Add the items to the menu. */
		this.fileMenu.getItems().add(this.fileOpenMenuItem);
		this.fileMenu.getItems().add(this.fileSaveMenuItem);
		this.fileMenu.getItems().add(this.fileExitMenuItem);

		/* Set menulistner. */
		this.fileOpenMenuItem.setOnAction(this.menuHandler);
		this.fileSaveMenuItem.setOnAction(this.menuHandler);
		this.fileExitMenuItem.setOnAction(this.menuHandler);

		/* Create the class menu. */
		this.classMenu = new Menu("_Class");
		this.classNewMenuItem = new MenuItem("New class");
		this.classImportMenuItem = new MenuItem("Import class");

		/* Set shortcut keys. */
		this.classMenu.setAccelerator(KeyCombination.keyCombination("SHORTCUT+C"));

		/* Add the items to the menu. */
		this.classMenu.getItems().add(this.classNewMenuItem);
		this.classMenu.getItems().add(this.classImportMenuItem);

		/* Set menulistner. */
		this.classNewMenuItem.setOnAction(this.menuHandler);
		this.classImportMenuItem.setOnAction(this.menuHandler);

		/* Create the package menu. */
		this.packageMenu = new Menu("_Package");
		this.packageImportMenuItem = new MenuItem("Import package");
		this.packageAddStatementMenuItem = new MenuItem("Add statement");

		/* Set shortcut keys. */
		this.packageMenu.setAccelerator(KeyCombination.keyCombination("SHORTCUT+P"));

		/* Add the items to the menu. */
		this.packageMenu.getItems().add(this.packageImportMenuItem);
		this.packageMenu.getItems().add(this.packageAddStatementMenuItem);

		/* Set menulistner. */
		this.packageImportMenuItem.setOnAction(this.menuHandler);
		this.packageAddStatementMenuItem.setOnAction(this.menuHandler);

		/* Create the tools menu. */
		this.toolsMenu = new Menu("_Tools");
		this.toolsDOSShellMenuItem = new MenuItem("_MSDOS Shell");

		/* Set shortcut keys. */
		this.toolsMenu.setAccelerator(KeyCombination.keyCombination("SHORTCUT+T"));
		this.toolsDOSShellMenuItem.setAccelerator(KeyCombination.keyCombination("SHORTCUT+M"));

		/* Add the items to the menu. */
		this.toolsMenu.getItems().add(this.toolsDOSShellMenuItem);

		/* Set menulistner. */
		this.toolsDOSShellMenuItem.setOnAction(this.menuHandler);

		/* Create the help menu. */
		this.helpMenu = new Menu("_Help");
		this.helpAboutMenuItem = new MenuItem("_About");

		/* Set shortcut keys. */
		this.helpMenu.setAccelerator(KeyCombination.keyCombination("SHORTCUT+H"));
		this.helpAboutMenuItem.setAccelerator(KeyCombination.keyCombination("SHORTCUT+A"));

		/* Add the items to the menu. */
		this.helpMenu.getItems().add(this.helpAboutMenuItem);

		/* Set menulistner. */
		this.helpAboutMenuItem.setOnAction(this.menuHandler);

		/* Add the items to the menubar. */
		this.menuBar.getMenus().add(this.fileMenu);
		this.menuBar.getMenus().add(this.classMenu);
		this.menuBar.getMenus().add(this.packageMenu);
		this.menuBar.getMenus().add(this.toolsMenu);
		this.menuBar.getMenus().add(this.helpMenu);

		/* Create the split pane. */
		this.guiSplitPane = new SplitPane();

		/* Create the tab pane. */
		this.guiTabPane = new TabPane();
		this.guiSplitPane.getItems().addAll(this.guiTabPane);

		/* Create initial blank tab. */
		this.CreateNewEditor(null, null, false);

		/* Create toolbox. */
		this.CreateToolBox();

		/* Create the main window's VBox. */
		this.guiVBox = new VBox();
		this.guiVBox.getChildren().addAll(	this.menuBar,
											this.guiSplitPane);

		/* Create the Stage and Scene. */
		this.guiStage = new Stage();
		this.guiScene = new Scene(this.guiVBox);
		this.guiStage.setTitle(this.MAIN_WINDOW_TITLE_TXT);
		this.guiStage.setScene(this.guiScene);

		/* Load the previous session if needed. */
		this.LoadPreviousSession();

		/* Exit function. */
		return;
	}
	
	public void start(Stage primaryStage) {
		/* Create the GUI. */
		CreateGUIObjects();

		/* Showtime. */
		if (this.guiStage != null) {
			this.guiStage.show();
		}

		/* Exit function. */
		return;
	}

	private Tab GetCurrentlySelectedTab() {
		/* Init vars. */
		Tab ret = null;				/* The result of this function. */

		/* Check for valid state. */
		if (this.guiTabPane != null) {
			/* Get the current tab.
				- Get the tab list from the tab pane.
				- Loop through it until we find the selected tab.
			*/
			for (int a = 0; ((a < (this.guiTabPane.getTabs()).size()) && (ret == null)); a++) {
				/* Get the tab. */
				ret = (this.guiTabPane.getTabs()).get(a);
				if ((ret != null) && (!(ret.isSelected()))) {
					/* This is not the selected tab.
						NULL out the reference so we can keep going. */
					ret = null;
				}
			}
		}

		/* Exit function. */
		return ret;
	}

	private EditorPane GetCurrentlySelectedEditorPane() {
		/* Init vars. */
		EditorPane ret = null;			/* The result of this function. */
		Tab tabFromList = null;			/* Reference to a tab. */
		Node tempNode = null;			/* Reference to a node. */

		/* Call GetCurrentlySelectedTab(). */
		tabFromList = this.GetCurrentlySelectedTab();
		if (tabFromList != null) {
			/* Get the editor pane. */
			tempNode = tabFromList.getContent();
			if ((tempNode != null) && (tempNode instanceof EditorPane)) {
				ret = ((EditorPane)tempNode);
			}
		}

		/* Exit function. */
		return ret;
	}

	private void LoadPreviousSession() {
		/* Init vars. */
		File sessionFile = new File(PREVIOUS_SESSION_FILENAME);
		DataInputStream sessionFileInputStream = null;
		BufferedInputStream sessionFileInputBufferStream = null;
		File editorFile = null;

		/* Check for valid state. */
		if (this.guiTabPane != null) {
			/* Begin try block. */
			try {
				/* Check for valid session file. */
				if (sessionFile.exists()) {
					/* Open the session file. */
					sessionFileInputBufferStream = new BufferedInputStream(
														new FileInputStream(sessionFile));
					sessionFileInputStream = new DataInputStream(sessionFileInputBufferStream);
												
					if (sessionFileInputStream != null) {
						/* Read previous session. */
						do {
							editorFile = new File(sessionFileInputStream.readUTF());
							if (editorFile != null) {
								/* Create new editor */
								this.CreateNewEditor(editorFile, null, false);
							}
						} while (sessionFileInputBufferStream.available() > 0);

						/* Close the file. */
						sessionFileInputStream.close();
						sessionFileInputStream = null;

						/* Delete the old session file. */
						sessionFile.delete();
					}
				}
			}
			catch (FileNotFoundException fnfe) {
				System.out.println("\nEXCEPTION: LoadPreviousSession(): Exception thrown: " +
				fnfe.getMessage());
			}
			catch (IOException ioe) {
				System.out.println("\nEXCEPTION: LoadPreviousSession(): Exception thrown: " +
				ioe.getMessage());
			}
			catch (Exception e) {
				System.out.println("\nEXCEPTION: LoadPreviousSession(): Exception thrown: " +
				e.getMessage());
			}
			finally {
				/* Close the session file if needed. */
				if (sessionFileInputStream != null) {
					try {
						sessionFileInputStream.close();
						sessionFileInputStream = null;
					}
					catch (IOException ioe) {
						System.out.println("\nEXCEPTION: LoadPreviousSession(): Exception thrown while closing session file: " +
						ioe.getMessage());
					}
				}
			}
		}

		/* Exit function. */
		return;
	}

	private void SaveAllOpenFiles() {
		/* Init vars. */
		boolean dataOutputed = false;	/* Whether or not data was written to the session file. */
		File sessionFile = new File(PREVIOUS_SESSION_FILENAME);
		DataOutputStream sessionFileOutputStream = null;
		File editorFile = null;			/* File object from the editor pane. */
		File untitledFile = null;		/* Used to check for a file name that is already used. (Most likely from a previous session.) */
		long untitledFileCount = 0;		/* Used to keep track of files that we have not titled yet. */
		Tab tabFromList = null;			/* Reference to a tab. */
		Node tempNode = null;			/* Reference to a node. */
		EditorPane currentEditor = null;			/* The editor we are working on. */
		String tempFilename = null;		/* Used to generate filenames. */

		/* Begin try block. */
		try {
			/* Create needed file streams for Session file. */
			sessionFileOutputStream = new DataOutputStream(
											new BufferedOutputStream(
												new FileOutputStream(sessionFile, false)));
			if (sessionFileOutputStream != null) {
				/* Begin save loop. */
				for (int a = 0; (a < (this.guiTabPane.getTabs()).size()); a++) {
					/* Get the tab. */
					tabFromList = (this.guiTabPane.getTabs()).get(a);
					if (tabFromList != null) {
						/* Get the editor pane. */
						tempNode = tabFromList.getContent();
						if (tempNode != null) {
							if (tempNode instanceof EditorPane) {
								/* Cast back to an EditorPane. */
								currentEditor = ((EditorPane)tempNode);

								/* Check and see if the editor has a valid file object. */
								editorFile = currentEditor.getCurrentFile();
								if (editorFile != null) {
									/* Get the file name and add it to the sessionFile. */
									sessionFileOutputStream.writeUTF(editorFile.getAbsolutePath());
									dataOutputed = true;

									/* Tell the editor to save it's file. */
									currentEditor.saveCurrentFile();
								}
								else {
									/* Check and see if the editor pane is empty. (No need to create a bunch of empty files.) */
									if (!(currentEditor.isEmpty())) {
										/* Default handler for unnamed files. */
										if ((tabFromList.getText()).indexOf(UNTITLED_FILE_TXT) == 0) {
											/* Reset untitledFile. */
											untitledFile = null;

											/* Begin untitled file name generation loop. */
											do {
												/* Generate an untitled file name. */
												tempFilename = new String(	this.UNTITLED_FILE_TXT +
																			Long.toString(untitledFileCount) +
																			this.TEXT_FILE_EXTENSION_TXT);

												/* Create file object. */
												untitledFile = new File(tempFilename);
												if (untitledFile != null) {
													/* Check and see if the file exists. */
													if (!(untitledFile.exists())) {
														/* File does not exist, 
															write the filename back to the sessionFile. */
														sessionFileOutputStream.writeUTF(tempFilename);
														dataOutputed = true;

														/* Set the generated file to the currentEditor. */
														currentEditor.setCurrentFile(untitledFile);
													}
													else {
														/* This file already exists, increment count. */
														untitledFileCount++;

														/* Blank out untitledFile so we can try again. */
														untitledFile = null;
													}
												}
											} while (untitledFile == null);

											/* Tell the editor to save it's file. */
											currentEditor.saveCurrentFile();
										}
										else {
											/*	Use the tab name as the file name.
												Get the tab name. */
											untitledFile = new File((tabFromList.getText()));
											if (untitledFile != null) {
												/* Write the filename back to the sessionFile. */
												sessionFileOutputStream.writeUTF((tabFromList.getText()));
												dataOutputed = true;

												/* Set the generated file to the currentEditor. */
												currentEditor.setCurrentFile(untitledFile);

												/* Tell the editor to save it's file. */
												currentEditor.saveCurrentFile();
											}
										}
									}
								}
							}
						}
					}
				}
			}
		}
		catch (FileNotFoundException fnfe) {
			System.out.println("\nEXCEPTION: SaveAllOpenFiles(): Exception thrown: " +
			fnfe.getMessage());
		}
		catch (IOException ioe) {
			System.out.println("\nEXCEPTION: SaveAllOpenFiles(): Exception thrown: " +
			ioe.getMessage());
		}
		catch (Exception e) {
			System.out.println("\nEXCEPTION: SaveAllOpenFiles(): Exception thrown: " +
			e.getMessage());
		}
		finally {
			/* Close the session file if needed. */
			if (sessionFileOutputStream != null) {
				try {
					sessionFileOutputStream.close();
					sessionFileOutputStream = null;

					/* Check and see if the session file has data in it. */
					if (!(dataOutputed)) {
						/* Delete the session file it's a pointless empty file. */
						sessionFile.delete();
					}
				}
				catch (IOException ioe) {
					System.out.println("\nEXCEPTION: SaveAllOpenFiles(): Exception thrown while closing session file: " +
					ioe.getMessage());
				}
				catch (Exception e) {
					System.out.println("\nEXCEPTION: SaveAllOpenFiles(): Exception thrown: " +
					e.getMessage());
				}
			}
		}

		/* Exit function. */
		return;
	}

	private Stage CreatePluginFunctionSelectionStage(String[] pluginFunctionList) {
		/* Init vars. */
		Stage ret = null;							/* The result of this function. */
		Scene retScene = null;						/* The scene for the result. */
		Button ok_button = null;					/* OK Button for the scene. */
		Button cancel_button = null;				/* Cancel Button for the scene. */
		RadioButton tempRadioButton = null;			/* RadioButton used to create the radio buttons for the functions. */
		ToggleGroup radioButtonToggleGroup = null;	/* ToggleGroup for the created radio buttons. */
		VBox radioButtonVBox = null;				/* VBox for the created radio buttons. */
		Label instructionsLBL = null;				/* Instructions label. */
		ScrollPane radioButtonSP = null;			/* ScrollPane for the created radio buttons. */
		HBox buttonsHBox = null;					/* HBox for the buttons. */
		VBox retVBox = null;						/* Root node for the window. */

		/* Check for valid args. */
		if (pluginFunctionList.length > 0) {
			/* Begin try block. */
			try {
				/* Create the togglegroup. */
				radioButtonToggleGroup = new ToggleGroup();
				if (radioButtonToggleGroup != null) {
					/* Create the radioButtonVBox. */
					radioButtonVBox = new VBox();
					if (radioButtonVBox != null) {
						for (int x = 0; (x < pluginFunctionList.length); x++) {
							/* Reset tempRadioButton. */
							tempRadioButton = null;

							/* Check for valid string. */
							if (pluginFunctionList[x] != null) {
								/* Create the new button. */
								tempRadioButton = new RadioButton(pluginFunctionList[x]);
								if (tempRadioButton != null) {
									/* Add the radio button to the VBox. */
									(radioButtonVBox.getChildren()).add(tempRadioButton);

									/* Add the radio button to the togglegroup. */
									tempRadioButton.setToggleGroup(radioButtonToggleGroup);
								}
							}
						}
					}

					/* Check and see if we created anything. */
					if (((radioButtonVBox.getChildren()).size()) > 0) {
						/* Create the ScrollPane for the radio buttons. */
						radioButtonSP = new ScrollPane();
						if (radioButtonSP != null) {
							/* Set the radioButtonVBox into the radioButtonSP. */
							radioButtonSP.setContent(radioButtonVBox);

							/* Create the instructions label. */
							instructionsLBL = new Label(PLUGIN_FUNCTION_SELECTION_WINDOW_INSTRUCTIONS_LBL);
							if (instructionsLBL != null) {
								/* Now create the ok and cancel buttons. */
								ok_button = new Button(OK_TXT);
								if (ok_button != null) {
									/* Create the handler for the button. */
									ok_button.setOnAction(new EventHandler<ActionEvent>() {
										@Override
										public void handle(ActionEvent event) {
											/* Init vars. */
											boolean done = false;
											Object sourceObject = null;
											Button sourceButton = null;
											Node tempNode = null;
											RadioButton tempRadioButton = null;
											Scene sourceScene = null;
											Parent sourceParent = null;
											Window sourceWindow = null;
											ObservableList<Node> sourceNodeList = null;
											ObservableList<Node> sourceVBoxNodeList = null; 
											ObservableList<Node> radioButtonVBoxNodeList = null;
											VBox retVBox = null;
											ScrollPane sourceSP = null;
											VBox radioButtonVBox = null;
											String functionName = null;

											/* Get the event source. */
											sourceObject = event.getSource();
											if ((sourceObject != null) && (sourceObject instanceof Button)) {
												/* Get the button. */
												sourceButton = (Button)sourceObject;

												/* Get the button's scene. */
												sourceScene = sourceButton.getScene();
												if (sourceScene != null) {
													/* Get the scene's root node. */
													sourceParent = sourceScene.getRoot();
													if (sourceParent != null) {
														/* Get the node list. */
														sourceNodeList = sourceParent.getChildrenUnmodifiable();
														if ((sourceNodeList != null) && (sourceNodeList.size() == 1) &&
															((sourceNodeList.get(0)) != null) &&
															((sourceNodeList.get(0)) instanceof VBox)) {
																/* Get back the root node VBox. */
																retVBox = (VBox)sourceNodeList.get(0);

																/* Get back the VBox's children. */
																sourceVBoxNodeList = retVBox.getChildren();
																if (sourceVBoxNodeList != null) {
																	for (int x = 0; ((!done) && (x < sourceVBoxNodeList.size())); x++) {
																		/* Find the ScrollPane. */
																		if (((sourceVBoxNodeList.get(x)) != null) &&
																			((sourceVBoxNodeList.get(x)) instanceof ScrollPane)) {
																				/* Get the ScrollPane. */
																				sourceSP = (ScrollPane)sourceVBoxNodeList.get(x);
																				if (sourceSP != null) {
																					/* Get the radio buttons list. */
																					tempNode = sourceSP.getContent();
																					if ((tempNode != null) && (tempNode instanceof VBox)) {
																						/* Get the VBox, reset tempNode, get the node list from the VBox. */
																						radioButtonVBox = (VBox)tempNode;
																						tempNode = null;
																						radioButtonVBoxNodeList = radioButtonVBox.getChildren();
																						if (radioButtonVBoxNodeList != null) {
																							/* Begin loop to find the selected radio button. */
																							for (int y = 0; ((!done) && (y < radioButtonVBoxNodeList.size())); y++) {
																								/* Check for null and radio button. */
																								tempNode = radioButtonVBoxNodeList.get(y);
																								if ((tempNode != null) && (tempNode instanceof RadioButton)) {
																									tempRadioButton = (RadioButton)tempNode;

																									/* Check and see if the radio button is selected. */
																									if (tempRadioButton.isSelected()) {
																										/* Found the selected radio button! (At last....) 
																											Get the thing's name.
																										*/
																										functionName = tempRadioButton.getText();
																										if (functionName != null) {
																											System.out.println("Selected Function Name: " + functionName);
																											/* We need to check and see if the user is setting args for the function. */
																											/*if () {
																												/* Check and see if the user wants to set the required arguments. */
																											//	if (toolboxRequiredArgsCheckbox.isSelected()) {
																													/* Check and see if the plugin has required args for the function call. */
																													/* functionHasRequiredArguements() */
																												//	if (tempPlugin.hasRequiredArguements()) {
																														/* Get the stage for the required arguments. */
																														/* getFunctionRequiredArguementsStage() */
																												//		pluginStage = tempPlugin.getRequiredArguementsStage();
																												//		if (pluginStage != null) {
																															/* Show the stage. */
																												//			pluginStage.showAndWait();
																													//	}
																												//	}
																												//}

																												/* Check and see if the user wants to set the extra arguments. */
																											//	if (toolboxExtraArgsCheckbox.isSelected()) {
																													/* Check and see if the plugin has extra args for the function call. */
																													/* functionHasExtraOptions() */
																												//	if (tempPlugin.hasExtraOptions()) {
																														/* Get the stage for the extra arguments. */
																														/* getFunctionExtraOptionsStage() */
																													//	pluginStage = tempPlugin.getExtraOptionsStage();
																													//	if (pluginStage != null) {
																															/* Show the stage. */
																														//	pluginStage.showAndWait();
																											//			}
																											//		}
																											//	}
																												
																												/* Generate the function call. */
																												//currentEditor.addLine(((tempPlugin.generateFunctionCall()) + "\n"));
																									//		}
																										}

																										/* Done. */
																										done = true;
																									}
																								}
																							}
																						}
																					}
																				}
																		}
																	}
																}
														}
													}

													/* Get the window. */
													sourceWindow = sourceScene.getWindow();
													if (sourceWindow != null) {
														/* Hide (close) the window. */
														sourceWindow.hide();
													}
												}
											}

											/* Exit function. */
											return;
										}
									});
									
									/* Cancel button. */
									cancel_button = new Button(CANCEL_TXT);
									if (cancel_button != null) {
										/* Create the handler for the button. */
										cancel_button.setOnAction(new EventHandler<ActionEvent>() {
											@Override
											public void handle(ActionEvent event) {
												/* Init vars. */
												Object sourceObject = null;
												Button sourceButton = null;
												Scene sourceScene = null;
												Window sourceWindow = null;

												/* Get the event source. */
												sourceObject = event.getSource();
												if ((sourceObject != null) && (sourceObject instanceof Button)) {
													/* Get the button. */
													sourceButton = (Button)sourceObject;

													/* Get the button's scene. */
													sourceScene = sourceButton.getScene();
													if (sourceScene != null) {
														/* Get the window. */
														sourceWindow = sourceScene.getWindow();
														if (sourceWindow != null) {
															/* Hide (close) the window. */
															sourceWindow.hide();
														}
													}
												}

												/* Exit function. */
												return;
											}
										});

										/* Create the HBox. */
										buttonsHBox = new HBox();
										if (buttonsHBox != null) {
											buttonsHBox.getChildren().addAll(	ok_button,
																				cancel_button);

											/* Create the retVBox. */
											retVBox = new VBox();
											if (retVBox != null) {
												retVBox.getChildren().addAll(	instructionsLBL,
																				radioButtonSP,
																				buttonsHBox);

												/* Create the scene. */
												retScene = new Scene(retVBox);
												if (retScene != null) {
													/* Create the stage. */
													ret = new Stage();
													if (ret != null) {
														ret.setTitle(PLUGIN_FUNCTION_SELECTION_WINDOW_TITLE_TXT);
														ret.setScene(retScene);
														ret.initModality(Modality.WINDOW_MODAL);
													}
												}
											}
										}
									}
								}
							}
						}
					}
				}
			}
			catch (Exception e) {
				System.out.println("\nEXCEPTION: CreatePluginFunctionSelectionStage(): Exception thrown: " +
				e.getMessage());
			}
		}

		/* Exit function. */
		return ret;
	}

	private class MenuHandler implements EventHandler<ActionEvent> {
		@Override
		public void handle(ActionEvent event) {
			/* Declare vars. */
			boolean deleteOldFile = false;	/* Whether or not to delete an old file after a successful write. */
			boolean done = false;			/* Used to abort the plugin search loop. */
			Object sourceObject = null;		/* Object pointer to the object that created the event. */
			MenuItem sourceMenuItem = null;	/* MenuItem that created the event. */
			FileChooser fileChooser = null;	/* File chooser used to select files. */
			File file = null;				/* File that was selected by the user. */
			File previousEditorFile = null;	/* File object from the editor. */
			EditorPane currentEditor = null;	/* The currently selected editor in the GUI. */
			Tab currentTab = null;				/* The currently selected tab in the GUI. */
			MessageWindow msgWin = null;	/* Used to display messages to the user. */
			TextInputWindow tiWin = null;	/* Used to get text from the user. */
			String userInputString = null;	/* Text input from user. */
			ProcessBuilder processBuilder = null;	/* Used to spawn new processes. */

				/* Get the source of the event. */
				sourceObject = event.getSource();
				if (sourceObject != null) {
					/* Determine what the source object is. */
					if (sourceObject instanceof MenuItem) {
						/* Cast the pointer. */
						sourceMenuItem = (MenuItem)sourceObject;

						/*
						private MenuItem helpAboutMenuItem;*/
						/* OK now we need to figure out what menu item was triggered. */
						if (sourceMenuItem == fileOpenMenuItem)
						{
							/* Ask user for a file to open. */
							fileChooser = new FileChooser();
							if (fileChooser != null) {
								fileChooser.getExtensionFilters().addAll(
									new FileChooser.ExtensionFilter("Java Source Code Files", "*.java", "*.jav"),
									new FileChooser.ExtensionFilter("Text Files", "*.txt"),
									new FileChooser.ExtensionFilter("All Text Files", "*.*"));
								file = fileChooser.showOpenDialog(null);
								if (file != null) {
									CreateNewEditor(file, null, true);
								}
								fileChooser = null;
							}
						}
						else {
							if (sourceMenuItem == fileSaveMenuItem) {
								/* Ask the user for a file to save. */
								/* Ask user for a file to open. */
								fileChooser = new FileChooser();
								if (fileChooser != null) {
									fileChooser.getExtensionFilters().addAll(
										new FileChooser.ExtensionFilter("Java Source Code Files", "*.java", "*.jav"),
										new FileChooser.ExtensionFilter("Text Files", "*.txt"),
										new FileChooser.ExtensionFilter("All Text Files", "*.*"));
									file = fileChooser.showSaveDialog(null);
									if (file != null) {
										/* Get the currently selected tab's editor pane. */
										currentEditor = GetCurrentlySelectedEditorPane();
										if (currentEditor != null) {
											/* Get the original file name from the editor. */
											previousEditorFile = currentEditor.getCurrentFile();
											if (previousEditorFile != null) {

												/* Check and see if the editor file is an old untitled file. */
												if ((previousEditorFile.getName()).indexOf(UNTITLED_FILE_TXT) == 0) {
													/* This file needs to be deleted after a successful write. */
													deleteOldFile = true;
												}
											}

											/* Set the file for the editor. */
											currentEditor.setCurrentFile(file);

											/* Save the file. */
											currentEditor.saveCurrentFile();

											/* Need to change the tab title. */
											currentTab = GetCurrentlySelectedTab();
											if (currentTab != null) {
												currentTab.setText(file.getName());
												currentTab = null;
											}

											/* Check and see if we need to delete the old temp file. */
											if (deleteOldFile) {
												/* Check for a successful write. */
												if (file.length() >= currentEditor.getTextLength()) {
													/* Delete old temp file. */
													previousEditorFile.delete();
												}
											}
										}
									}
									fileChooser = null;
								}
							}
							else {
								if (sourceMenuItem == fileExitMenuItem) {
									/* Save the session and all open files. */
									SaveAllOpenFiles();

									/* Close the window. */
									if (guiStage != null) {
										guiStage.close();
									}
								}
								else {
									/* Combined method for handling Package / Class import, New Class, and Package statements.
										I.e. Handling The class and package menu's menu items.
									*/
									if ((sourceMenuItem == classNewMenuItem) || (sourceMenuItem == classImportMenuItem) || 
										(sourceMenuItem == packageImportMenuItem) || (sourceMenuItem == packageAddStatementMenuItem)) {
										/* Ask user for the class / package name. */
										try {
											tiWin = new TextInputWindow(/* Window Title to display based on sourceMenuItem. */
																		((sourceMenuItem == classNewMenuItem) ? (NEW_CLASS_NAME_WINDOW_TITLE_TXT) :
																			((sourceMenuItem == classImportMenuItem) ? (IMPORT_CLASS_NAME_WINDOW_TITLE_TXT) :
																			((sourceMenuItem == packageImportMenuItem) ? (IMPORT_PACKAGE_NAME_WINDOW_TITLE_TXT) :
																				(PACKAGE_STATEMENT_WINDOW_TITLE_TXT)))),
																		/* Message to display depending on sourceMenuItem. */
																		(((sourceMenuItem == classNewMenuItem) || (sourceMenuItem == classImportMenuItem)) ? (CLASS_NAME_WINDOW_TXT) :
																			(PACKAGE_NAME_WINDOW_TXT)),
																		/* Display entire message. */
																		true);
											if (tiWin != null) {
												userInputString = tiWin.ShowMessage(guiStage);
												if ((userInputString.trim()).length() > 0) {
													/* If the user chose to create a new class we need to create a new editor. */
													if (sourceMenuItem == classNewMenuItem) {
														/* Create the new EditorPane with no file object,
															the user's given class name + .java as the tab name,
															and select it's tab in the GUI. */
														CreateNewEditor(null, ((userInputString.trim()) + JAVA_FILE_EXTENSION_TXT), true);
													}

													/* Get the new tab's editor pane. */
													currentEditor = GetCurrentlySelectedEditorPane();
													if (currentEditor != null) {
														/* Create import statement. */
														currentEditor.addLine(	/* Statement to add based on sourceMenuItem. */
																				((sourceMenuItem == classNewMenuItem) ? (CLASS_DECLARATION_PART_1_TXT) :
																				(((sourceMenuItem == classImportMenuItem) || (sourceMenuItem == packageImportMenuItem)) ? (IMPORT_STATEMENT_TXT) :
																					(PACKAGE_STATEMENT_TXT))) +
																				/* Variable name to add. */
																				(userInputString.trim()) +
																				/* Final segment to add based on sourceMenuItem. */
																				((sourceMenuItem == classNewMenuItem) ? (CLASS_DECLARATION_PART_2_TXT) :
																				(SEMICOLON_TXT)));
													}
												}
												else {
													/* Bad class / package name. */
													msgWin = new MessageWindow(	ERROR_MESSAGE_WINDOW_TITLE_TXT,
																				/* Message to display based on sourceMenuItem. */
																			(((sourceMenuItem == classNewMenuItem) || (sourceMenuItem == classImportMenuItem)) ? (ERROR_MESSAGE_BAD_CLASS_NAME_TXT) :
																				(ERROR_MESSAGE_BAD_PACKAGE_NAME_TXT)),
																				/* Display entire message. */
																				true);
													if (msgWin != null) {
														msgWin.ShowMessage(guiStage);
														msgWin = null;
													}
												}
												tiWin = null;
											}
										}
										catch (Exception e) {
											System.out.println("\nEXCEPTION: Exception thrown while asking user for " +
											/* Message to display based on sourceMenuItem. */
											((sourceMenuItem == classImportMenuItem) ? ("class ") : ("package ")) + 
											"name: " + e.getMessage());
										}
									}
									else {
										if (sourceMenuItem == toolsDOSShellMenuItem) {
											/* Begin try block. */
											try {
												/* Create new processBuilder. */
												processBuilder = new ProcessBuilder(MSDOS_SHELL_CMD_TXT, MSDOS_SHELL_ARG_1_TXT, MSDOS_SHELL_ARG_2_TXT);
												if (processBuilder != null) {
													/* Create dos shell process. */
													processBuilder.start();
												}
											}
											catch (NullPointerException npe) {
												/* Give it some args man.... */
												System.out.println("\nEXCEPTION: Exception thrown while attempting to execute nothing with a ProcessBuilder.....");
											}
											catch (SecurityException se) {
												System.out.println("\nEXCEPTION: Exception thrown while attempting to execute a command with a ProcessBuilder: Access Denied.");
											}
											catch (IOException ioe) {
												System.out.println("\nEXCEPTION: Exception thrown while attempting to execute a command with a ProcessBuilder: " +
												ioe.getMessage());
											}
											catch (IndexOutOfBoundsException ioobe) {
												System.out.println("\nEXCEPTION: Exception thrown while attempting to execute a command with a ProcessBuilder: " +
												ioobe.getMessage());
											}
											catch (Exception e) {
												System.out.println("\nEXCEPTION: Exception thrown while attempting to execute a command with a ProcessBuilder: " +
												e.getMessage());
											}
										}
										else {
											if (sourceMenuItem == helpAboutMenuItem) {
												/* Create new message window for the about data. */
												try {
													msgWin = new MessageWindow(ABOUT_WINDOW_TITLE_TXT,
																				ABOUT_WINDOW_TXT,
																				true);
													if (msgWin != null) {
														msgWin.ShowMessage(guiStage);
														msgWin = null;
													}
												}
												catch (Exception e) {
													System.out.println("\nEXCEPTION: Exception thrown while attempting to display about window: " +
													e.getMessage());
												}
											}
										}
									}
								}
							}
						}
					}
				}
		}
	}

	private class ButtonHandler implements EventHandler<ActionEvent> {
		@Override
		public void handle(ActionEvent event) {
			/* Declare vars. */
			boolean done = false;			/* Used to abort the plugin search loop. */
			Object sourceObject;			/* Object pointer to the object that created the event. */
			Button sourceButton;			/* Pointer to the button obejct that was clicked. */
			String toolName = null;			/* Name of the toolbox object that was clicked. */
			EditorGUIToolBoxPlugin tempPlugin = null;	/* Reference to the plugin object. */
			String varName = null;			/* The user's variable name for the object. */
			TextInputWindow tiWin = null;	/* Window for geting data from the user. */
			EditorPane currentEditor = null;	/* The currently selected editor pane in the GUI. */
			Stage pluginStage = null;			/* Used to open windows for plugins. */
			String[] pluginFunctionList = null;	/* Used to get the list of supported functions from the plugin. */
			ObservableList<Node> toolboxButtons = null; /* A Node list of the toolbox's buttons. */
			Node tempNode = null;				/* Used to access Node objects. */

			/* Check for valid state. */
			if ((toolboxGP != null) && (guiTabPane != null) &&
			(loadedToolboxPluginClasses != null) &&
			(loadedToolboxPlugins != null)) {
				/* Get the source of the event. */
				sourceObject = event.getSource();
				if (sourceObject != null) {
					/* Determine what the source object is. */
					if (sourceObject instanceof Button) {
						/* Cast the pointer. */
						sourceButton = (Button)sourceObject;

						/* OK now we need to get the tool name for the clicked on tool. */
						toolName = sourceButton.getText();
						if (toolName != null) {
							/* Begin search loop. */
							for (int x = 0; ((x < loadedToolboxPlugins.size()) && (!done)); x++) {

								/* Get the plugin reference. */
								tempPlugin = loadedToolboxPlugins.elementAt(x);
								if (tempPlugin != null) {

									/* Check for a toolname match. */
									if (toolName.equals((tempPlugin.getToolboxButtonLabel()))) {

										/* Get the currently selected tab's editor pane. */
										currentEditor = GetCurrentlySelectedEditorPane();
										if (currentEditor != null) {
											/* Make sure we have something to do. */
											if ((toolboxDeclarationCheckbox.isSelected()) ||
												(toolboxInitializationCheckbox.isSelected()) ||
												(toolboxFunctionCallCheckbox.isSelected())) {

												/* Begin try block for the TextInputWindow. */
												try {
													/* Ask the user to create a variable name for the object. */
													tiWin = new TextInputWindow(VARIABLE_NAME_WINDOW_TITLE_TXT,
																				VARIABLE_NAME_WINDOW_TXT);
													if (tiWin != null) {
														/* Display the message to the user and allow them to name the variable. */
														varName = tiWin.ShowMessage();

														/*! TODO: 

															- Actually add support beyond what was required for Assignment 03:
																- Extra Options
																- Required Arguements
																- Function calls

															Thinking about adding tabs for the toolbox to allow setting
															which dialogs to show, or maybe use a RadioButton with
															keyboard shortcuts for to make the selection.

															(Or maybe just use CheckBoxes and show the
															selected dialogs in succession.....)
														*/

														/* Check and see if the toolboxDeclarationCheckbox is checked. */
														if (toolboxDeclarationCheckbox.isSelected()) {
															/* Call generateDeclarationCode(), add the result to the
																current tab's editor pane. */
															currentEditor.addLine(((tempPlugin.generateDeclarationCode(varName)) + "\n"));
														}

														/* Check and see if the toolboxInitializationCheckbox is checked. */
														if (toolboxInitializationCheckbox.isSelected()) {
															/* Check and see if the user wants to set the required arguments. */
															if (toolboxRequiredArgsCheckbox.isSelected()) {
																/* Check and see if the plugin has required args for the initilization code. */
																if (tempPlugin.hasRequiredArguements()) {
																	/* Get the stage for the required arguments. */
																	pluginStage = tempPlugin.getRequiredArguementsStage();
																	if (pluginStage != null) {
																		/* Show the stage. */
																		pluginStage.showAndWait();
																	}
																}
															}

															/* Check and see if the user wants to set the extra arguments. */
															if (toolboxExtraArgsCheckbox.isSelected()) {
																/* Check and see if the plugin has extra args for the initilization code. */
																if (tempPlugin.hasExtraOptions()) {
																	/* Get the stage for the extra arguments. */
																	pluginStage = tempPlugin.getExtraOptionsStage();
																	if (pluginStage != null) {
																		/* Show the stage. */
																		pluginStage.showAndWait();
																	}
																}
															}

															/* Call generateInitializationCode(), add the result to the
																current tab's editor pane. */
															currentEditor.addLine(((tempPlugin.generateInitializationCode(varName)) + "\n"));
														}

														/* Check and see if the toolboxFunctionCallCheckbox is checked. */
														if (toolboxFunctionCallCheckbox.isSelected()) {
															/* Check and see if there are any functions that the plugin can generate code for. */
															if (tempPlugin.numberOfFunctionsToCall() > 0) {
																/* Get the function list. */
																pluginFunctionList = tempPlugin.getFunctionList();
																if (pluginFunctionList != null) {
																	/* Create and show the function call selection stage for the user. */
																	pluginStage = CreatePluginFunctionSelectionStage(pluginFunctionList);
																	if (pluginStage != null) {
																		/* Set the owner of the function call selection stage,
																			show it, and wait for user to finish.
																			(Code generation is done in the function call selection stage,
																			 NOT here.)
																		*/
																		pluginStage.initOwner(guiStage);
																		pluginStage.showAndWait();
																	}
																}
															}
														}

														/* Done. */
														done = true;
													}
												}
												catch (Exception e) {
												System.out.println("EXCEPTION: Exception thrown while attempting " +
													"to ask the user for a variable name.\n The thrown exception was: " +
													e.getMessage());
												}
											}
											else {
												/* Nothing to do. */
											}
										}
									}
								}
							}
						}
					}
					else {
						if (sourceObject instanceof CheckBox) {
							if ((sourceObject == toolboxDeclarationCheckbox) ||
								(sourceObject == toolboxInitializationCheckbox) ||
								(sourceObject == toolboxFunctionCallCheckbox)) {
								/* Determine if the all of the above checkboxes are inactive. */
								if ((!(toolboxDeclarationCheckbox.isSelected())) &&
									(!(toolboxInitializationCheckbox.isSelected())) &&
									(!(toolboxFunctionCallCheckbox.isSelected()))) {
									/* Get the toolbox buttons. */
									toolboxButtons = toolboxGP.getChildren();
									if (toolboxButtons != null) {
										/* Disable the toolbox. */
										for (int x = 0; (x < toolboxButtons.size()); x++) {
											/* Get the node. */
											tempNode = toolboxButtons.get(x);
											/* Check for null and instanceof Button. */
											if ((tempNode != null) && (tempNode instanceof Button)) {
												/* Disable the button. */
												((Button)(tempNode)).setDisable(true);
											}
										}
									}
								}
								else {
									/* Get the toolbox buttons. */
									toolboxButtons = toolboxGP.getChildren();
									if (toolboxButtons != null) {
										/* Enable the toolbox. */
										for (int x = 0; (x < toolboxButtons.size()); x++) {
											/* Get the node. */
											tempNode = toolboxButtons.get(x);
											/* Check for null and instanceof Button. */
											if ((tempNode != null) && (tempNode instanceof Button)) {
												/* Enable the button. */
												((Button)(tempNode)).setDisable(false);
											}
										}
									}
								}
							}
						}
					}
				}
			}
		}
	}	/* End of class ButtonHandler. */

}	/* End of EditorGUI. */
