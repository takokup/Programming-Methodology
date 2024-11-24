
/*
 * File: NameSurferGraph.java
 * ---------------------------
 * This class represents the canvas on which the graph of
 * names is drawn. This class is responsible for updating
 * (redrawing) the graphs whenever the list of entries changes or the window is resized.
 */

import acm.graphics.*;
import java.awt.event.*;
import java.util.*;
import java.awt.*;

public class NameSurferGraph extends GCanvas implements NameSurferConstants, ComponentListener {
	private ArrayList<NameSurferEntry> names = new ArrayList<NameSurferEntry>();

	/**
	 * Creates a new NameSurferGraph object that displays the data.
	 */
	public NameSurferGraph() {
		addComponentListener(this);
		names = new ArrayList<NameSurferEntry>();
		// You fill in the rest //
	}

	/**
	 * Clears the list of name surfer entries stored inside this class.
	 */
	public void clear() {
		names.clear();
		update();
		
		for(int i = 0;i < NDECADES;i++){
			drawGraph(names.get(i),i);
		}
	}
/*
 * draw a graph after entering the name
 */
	private void drawGraph(NameSurferEntry entry, int i) {
		

		for( int j = 0; j < NDECADES - 1; j++){

		GLine line = new GLine(getWidth() / NDECADES * j, rankCoordinates(entry.getRank(j)),
		getWidth() / NDECADES * (j + 1),rankCoordinates(entry.getRank(j +1)));

		if(i % 3 == 0){
		line.setColor(Color.BLACK);
		}else if(i % 4 == 1){
		line.setColor(Color.RED);
		}else if(i % 4 == 2){
		line.setColor(Color.BLUE);
		}
		add(line);
		}
		for( int m = 0; m < NDECADES; m++){
			String labelEntry = "";

			if(entry.getRank(m) != 0){
			labelEntry = entry.getName() + " " + entry.getRank(m);
			}else{
			labelEntry = entry.getName() + "*";
			}

			GLabel nameLabel = new GLabel(labelEntry,getWidth() / NDECADES * m,rankCoordinates(entry.getRank(m)));
			if(i % 4 == 0){
			nameLabel.setColor(Color.BLUE);
			}else if(i % 4 == 1){
			nameLabel.setColor(Color.RED);
			}else if(i % 4 == 2){
			nameLabel.setColor(Color.MAGENTA);
			}else if(i % 4 == 3){
			nameLabel.setColor(Color.BLACK);
			}
			add(nameLabel);
			}
		
	}
/*
 * returns graph's lines coordinates
 */
	private double rankCoordinates(int rank){
		double rnk= rank;

		if(rank != 0){
		rnk= (rnk/ MAX_RANK)* ( getHeight() - 2 * GRAPH_MARGIN_SIZE) + GRAPH_MARGIN_SIZE;
		}else{
		rnk= getHeight() - GRAPH_MARGIN_SIZE;
		}

		return rnk;
		}
	/* Method: addEntry(entry) */
	/**
	 * Adds a new NameSurferEntry to the list of entries on the display. Note
	 * that this method does not actually draw the graph, but simply stores the
	 * entry; the graph is drawn by calling update.
	 */
	public void addEntry(NameSurferEntry entry) {
		names.add(entry);
	}

	/**
	 * Updates the display image by deleting all the graphical objects from the
	 * canvas and then reassembling the display according to the list of
	 * entries. Your application must call update after calling either clear or
	 * addEntry; update is also called whenever the size of the canvas changes.
	 */
	public void update() {
		removeAll();
		drawBackground();
		for( int i = 0; i < names.size(); i++){

			drawGraph(names.get(i),i);

			}
	}
/*
 * draws the initial position of NameSurfer
 */
	private void drawBackground() {

		GLine horisontal1 = new GLine(0, GRAPH_MARGIN_SIZE, getWidth(), GRAPH_MARGIN_SIZE);
		GLine horisontal2 = new GLine(0, getHeight() - GRAPH_MARGIN_SIZE, getWidth(), getHeight() - GRAPH_MARGIN_SIZE);
		add(horisontal1);
		add(horisontal2);
		for(int i = 0;i<NDECADES;i++){
		GLine verticals = new GLine(0 +i*getWidth()/11,0,0 +i*getWidth()/11,getHeight());
		add(verticals);
		date();
		}
	}
	/*
	 * addes the date label 
	 */
	private void date(){

		for(int i = 0; i < NDECADES; i++){

		String dtString = Integer.toString(i * 10 + START_DECADE);
		GLabel dateLabel = new GLabel(dtString,i * getWidth() / NDECADES,getHeight() );
		add(dateLabel);
		}
	}

	/* Implementation of the ComponentListener interface */
	public void componentHidden(ComponentEvent e) {
	}

	public void componentMoved(ComponentEvent e) {
	}

	public void componentResized(ComponentEvent e) {
		update();
	}

	public void componentShown(ComponentEvent e) {
	}
}
