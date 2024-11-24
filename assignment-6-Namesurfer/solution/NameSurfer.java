
/*
 * File: NameSurfer.java
 * ---------------------
 * When it is finished, this program will implements the viewer for
 * the baby-name database described in the assignment handout.
 */

import acm.program.*;
import java.awt.event.*;
import javax.swing.*;

public class NameSurfer extends Program implements NameSurferConstants {
	private JTextField addname;
	private JLabel name;
	private JButton buttongraph;
	private JButton clear;
	private NameSurferGraph graph;

	/* Method: init() */
	/**
	 * This method has the responsibility for reading in the data base and
	 * initializing the interactors at the bottom of the window.
	 */
	public void init() {
		NameSurferGraph graph = new NameSurferGraph();
		add(graph);

		name = new JLabel("Name");
		add(name, SOUTH);
		addname = new JTextField(15);
		addname.addActionListener(this);
		add(addname, SOUTH);
		buttongraph = new JButton("Graph");
		add(buttongraph, SOUTH);
		clear = new JButton("Clear");
		add(clear, SOUTH);
		addActionListeners();

	}

	/* Method: actionPerformed(e) */
	/**
	 * This class is responsible for detecting when the buttons are clicked, so
	 * you will have to define a method to respond to button actions.
	 */
	public void actionPerformed(ActionEvent e) {

		if (e.getSource() == buttongraph) {
			println("Graph: " + addname.getText());

			NameSurferDataBase dataBase = new NameSurferDataBase("names-data.txt");
			NameSurferEntry entr = dataBase.findEntry(addname.getText().toString());

			println("Graph: " + entr.toString());

//			graph.addEntry(entr);      //these does not work 
//			graph.update();           //and I don't know why
			
		}
		if (e.getSource() == clear) {
			println("clear");
//			graph.clear();             //these does not work 
//			graph.update();           //and I don't know why
			
		} else if (e.getSource() == addname) {
			println("Graph: " + addname.getText());

			NameSurferDataBase dataBase = new NameSurferDataBase("names-data.txt");
			NameSurferEntry entr = dataBase.findEntry(addname.getText().toString());

			println("Graph: " + entr.toString());

			println("Graph: " + entr.toString());
//			graph.addEntry(entr);      //these does not work 
//			graph.update();           //and I don't know why
		
		}

	}

}
