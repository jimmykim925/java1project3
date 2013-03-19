package com.example.java1project3;

import java.util.ArrayList;

import android.content.Context;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Spinner;

public class FavDisplay extends LinearLayout{

		Button _add;
		Button _remove;
		Spinner _list;
		Context _context;
		
		// Array for string list
		ArrayList<String> _stocks = new ArrayList<String>();
		
		public FavDisplay(Context context){
			super(context);
			_context = context;
			
			LayoutParams lp;
			
			// add text to field
			_stocks.add("Select Favorite");
			
			// creates new spinner
			_list = new Spinner(_context);
			
			// set parameters
			lp = new LayoutParams(0, LayoutParams.WRAP_CONTENT, 1.0f);
			_list.setLayoutParams(lp);
			
			// array for layout
			ArrayAdapter<String> listAdapter = new ArrayAdapter<String>(_context, android.R.layout.simple_spinner_item, _stocks);
			listAdapter.setDropDownViewResource(android.R.layout.simple_dropdown_item_1line);
			_list.setAdapter(listAdapter);
			_list.setOnItemSelectedListener(new OnItemSelectedListener(){
				
				@Override
				public void onItemSelected(AdapterView<?> parent, View v,
						int pos, long id) {
					// TODO Auto-generated method stub
					Log.i("FAVORITE SELECTED", parent.getItemAtPosition(pos).toString());
				}
				
				@Override
				public void onNothingSelected(AdapterView<?> parent){
					Log.i("FAVORITE SELECTED", "NONE");
				}

				
			});
			
			updateFavorites();
			
			// add button and text
			_add = new Button(_context);
			_add.setText("+");
			
			_remove = new Button(_context);
			_remove.setText("-");
			
			// add views
			this.addView(_list);
			this.addView(_add);
			this.addView(_remove);
			
			lp = new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT);
			this.setLayoutParams(lp);
		}
		
		// add text to spinner
		private void updateFavorites(){
			_stocks.add("Redmond");
			_stocks.add("Seattle");
			_stocks.add("Bellevue");
		}
}
