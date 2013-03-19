package com.example.java1project3;

import android.content.Context;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;

public class SearchForm extends LinearLayout {
	
	// create search and button
	EditText _searchField;
	Button _searchButton;
	
	public SearchForm(Context context, String hint, String buttonText){
		super(context);
		
		// set layout parameter
		LayoutParams lp;
		
		// set search field, create new edit text field
		_searchField = new EditText(context);
		lp = new LayoutParams(0, LayoutParams.WRAP_CONTENT, 1.0f);
		// set up layout and hint
		_searchField.setLayoutParams(lp);
		_searchField.setHint(hint);
		
		_searchButton = new Button(context);
		_searchButton.setText(buttonText);
		
		// add views
		this.addView(_searchField);
		this.addView(_searchButton);
		
		lp = new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT);
		this.setLayoutParams(lp);
	}

		public EditText getField(){
			return _searchField;
			
		}
		
		public Button getButton(){
			return _searchButton;
		}
}
