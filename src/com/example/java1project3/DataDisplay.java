package com.example.java1project3;

import android.content.Context;
import android.widget.GridLayout;
import android.widget.TextView;

public class DataDisplay extends GridLayout {
	
	// create text views
	TextView _symbol;
	TextView _price;
	TextView _hist;
	Context _context;
	
	public DataDisplay(Context context){
		super(context);
		
		_context = context;
		
		// set columns
		this.setColumnCount(2);
		
		// create text views with set texts
		TextView symbolLabel = new TextView(_context);
		symbolLabel.setText("Enter a Zip Code Above to get geographic information");
		_symbol = new TextView(_context);
		
		TextView priceLabel = new TextView(_context);
		priceLabel.setText("\r\nGeographic data results for inputted area code........\r\n");
		_price = new TextView(_context);
		
		// add the text views
		this.addView(symbolLabel);
		this.addView(_symbol);
		this.addView(priceLabel);
		this.addView(_price);
		
		
		
	}
}
