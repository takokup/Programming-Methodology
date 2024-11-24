
/* 
 * File: FacePamphlet.java
 * -----------------------
 * When it is finished, this program will implement a basic social network
 * management system.
 */

import acm.program.*;
import acm.graphics.*;
import acm.util.*;
import java.awt.event.*;
import javax.swing.*;

public class FacePamphlet extends ConsoleProgram implements FacePamphletConstants {
	private JTextField addname;
	private JTextField changeStatus;
	private JButton statusButt;
	private JTextField changePicture;
	private JButton pictureButt;
	private JTextField addFriend;
	private JButton friendButt;
	private JLabel name;
	private JButton addButton;
	private JButton delete;
	private JButton lookup;
	private FacePamphletDatabase profDatabase;
	private FacePamphletProfile curr;
	private FacePamphletCanvas canvas = new FacePamphletCanvas();;

	/**
	 * This method has the responsibility for initializing the interactors in
	 * the application, and taking care of any other initialization that needs
	 * to be performed.
	 */
	public void init() {
		profDatabase = new FacePamphletDatabase();
		name = new JLabel("Name");
		add(name, NORTH);

		addname = new JTextField(TEXT_FIELD_SIZE);
		addname.addActionListener(this);
		add(addname, NORTH);

		addButton = new JButton("Add");
		add(addButton, NORTH);

		delete = new JButton("Delete");
		add(delete, NORTH);

		lookup = new JButton("Lookup");
		add(lookup, NORTH);

		changeStatus = new JTextField(TEXT_FIELD_SIZE);
		changeStatus.addActionListener(this);
		add(changeStatus, WEST);

		statusButt = new JButton("Change Status");
		add(statusButt, WEST);
		add(new JLabel(EMPTY_LABEL_TEXT), WEST);

		changePicture = new JTextField(TEXT_FIELD_SIZE);
		changePicture.addActionListener(this);
		add(changePicture, WEST);

		pictureButt = new JButton("Change Picture");
		add(pictureButt, WEST);
		add(new JLabel(EMPTY_LABEL_TEXT), WEST);

		addFriend = new JTextField(TEXT_FIELD_SIZE);
		addFriend.addActionListener(this);
		add(addFriend, WEST);

		friendButt = new JButton("Add Friend");
		add(friendButt, WEST);
		add(canvas);
		addActionListeners();

	}

	/**
	 * This class is responsible for detecting when the buttons are clicked or
	 * interactors are used, so you will have to add code to respond to these
	 * actions.
	 */
	public void actionPerformed(ActionEvent e) {
		if (e.getActionCommand().equals("Add")) {
			if(profDatabase.containsProfile(addname.getText())){
				println( "Add : Profile for " + addname.getText() + " aleady exists : " +  profDatabase.getProfile(addname.getText().toString()));
				curr= profDatabase.getProfile(addname.getText());
			}else {
				FacePamphletProfile prof = new FacePamphletProfile(addname.getText());
				profDatabase.addProfile(prof);
				println("add : new profile : " +  profDatabase.getProfile(addname.getText().toString()));
				curr= profDatabase.getProfile(addname.getText());
					
			    canvas.displayProfile(curr);
    			canvas.showMessage(" a  new profile was added : " + addname.getText());
    			
    			changeStatus.setText("");
    			addFriend.setText("");
			}

		} else if (e.getActionCommand().equals("Delete")) {
		
			if(profDatabase.containsProfile(addname.getText())){
				profDatabase.deleteProfile(addname.getText());
				println("Delete : profile of " + addname.getText() + "  deleted");
				curr= null;
				
			}else{
				println(" Delete : profile of " + addname.getText() + "  does not exists");
				curr=null;
			}

		} else if (e.getActionCommand().equals("Lookup")) {
			
			if(profDatabase.containsProfile(addname.getText())){
				println("Lookup : " + profDatabase.getProfile(addname.getText().toString()));
				curr= profDatabase.getProfile(addname.getText());
				
			}else{
				println("Lookup : profile of " + addname.getText() + "  does not exists");
				curr=null;
			}

		} else if (e.getActionCommand().equals("Change Status") || e.getSource() == changeStatus) {
			println("Status updated to  " + changeStatus.getText());
		
			if(curr != null){
				curr.setStatus(changeStatus.getText());
				
				if( curr != null){
					println(" ---- > Current profile : " + profDatabase.getProfile(addname.getText().toString()));
					}else{
					println("---- > No current profile");
					}
			}
			
		} else if (e.getActionCommand().equals("Change Picture") || e.getSource() == changePicture) {
			println("Change Picture: " + changePicture.getText());
			

				GImage image = null;
				if(curr != null){
				try{
				image = new GImage(changePicture.getText());
				curr.setImage(image);

				}catch(ErrorException ex){
				image = null;
				}
				if(image == null){
				println("Picture could not be updated");
				}else{
				println("Picture updated");
				}
				if(curr != null){
					
					
					if( curr != null){
						println(" ---- > Current profile : " + profDatabase.getProfile(addname.getText().toString()));
						}else{
						println("---- > No current profile");
						}
					}
				}else{
					if(curr != null){
						
						
						if( curr != null){
							println(" ---- > Current profile : " + profDatabase.getProfile(addname.getText().toString()));
							}else{
							println("---- > No current profile");
							}
				println("select which profile's picture you want to change");
				}

				
				}
		} else if (e.getActionCommand().equals("Add Friend") || e.getSource() == addFriend) {
			println("Add Friend: " + addFriend.getText());
			if(curr != null){
    			
    			
    			
    			
    			if(profDatabase.containsProfile(addFriend.getText())){
    				if(curr.addFriend(addFriend.getText())){
    					curr.addFriend(addFriend.getText());
    					FacePamphletProfile otherFr = profDatabase.getProfile(addFriend.getText());
    					otherFr.addFriend(curr.getName());
    				}
    			}
    			}
}
		
		}

    			

}
