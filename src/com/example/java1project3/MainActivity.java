// Jimmy Kim
// Java 1 Project 3
// Term 1303

package com.example.java1project3;

import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import java.util.HashMap;

import org.json.JSONException;
import org.json.JSONObject;

import com.example.lib.FileStuff;
import com.example.lib.WebStuff;

import android.os.AsyncTask;
import android.os.Bundle;
import android.app.Activity;
import android.content.Context;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import android.widget.LinearLayout;

public class MainActivity extends Activity {
	
	// create displays, layout
	Context _context;
	LinearLayout _appLayout;
	SearchForm _search;
	DataDisplay _stock;
	FavDisplay _favorites;
	Boolean _connected = false;
	
	// create hashmap for history of search results
	HashMap<String, String>_history;
	
	// Text view
	TextView ele;
	
	// Text view
	TextView hist;
	
	// Text view
	TextView showHist;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		_context = this;
		
		// linear layout
		_appLayout = new LinearLayout(this);
		
		// call history function for search results
		_history = getHistory();
		
		// READ history from file
		Log.i("HISTORY READ", _history.toString());
		
		// Get string from RESOURCE
		String stringResource = getString(R.string.text_zipcode);
		
		// create edit text field with hint
		_search = new SearchForm(_context, stringResource, "Submit");
		
		// Add search handler
		Button searchButton = _search.getButton();
		
		// event handler for click
		searchButton.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				
				// get json for geographic information
				getQuote(_search.getField().getText().toString());
			}
		}); 
		
		// detect network connection
		_connected = WebStuff.getConnectionStatus(_context);
		if(_connected){
			Log.i("NETWORK CONNECTION", WebStuff.getConnectionType(_context));
		}
		
		// Add display
		_stock = new DataDisplay(_context);
		
		// add favorites display
		_favorites = new FavDisplay(_context);
		
		// text views for section titles
		ele = new TextView(_context);
		hist = new TextView(_context);
		showHist = new TextView(_context);
		
		// set the text
		hist.setText("\r\nResults displayed after READING saved data from file for the search history...............\r\n");
		
		// add views to main layout
		_appLayout.addView(_search);
		_appLayout.addView(_stock);
		//_appLayout.addView(_favorites);
		_appLayout.addView(ele);
		_appLayout.addView(hist);
		_appLayout.addView(showHist);
		
		_appLayout.setOrientation(LinearLayout.VERTICAL);
		
		setContentView(_appLayout);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}
	
	private void getQuote(String symbol){
		//set up base url for api and pulling back a json file
		String baseURL = "http://api.wunderground.com/api/a64ac4c9694e7d2a/conditions/q/CA/" + symbol + ".json";
		String qs;
		try{
			// pass in baseURL
			qs = URLEncoder.encode(baseURL, "UTF-8");
		} catch (Exception e){
			Log.e("BAD URL", "ENCODING PROBLEM");
			qs="";
		}
		
		// create URL
		URL finalURL;
		try{
			// format json
			finalURL = new URL(baseURL + "?q=" + qs + "&format=json");
			QuoteRequest qr = new QuoteRequest();
			qr.execute(finalURL);
		} catch (MalformedURLException e){
			Log.e("BAD URL", "MALFORMED URL");
			finalURL = null;
		}
	}
	
	// READ saved file hashmap for search history
	@SuppressWarnings("unchecked")
	private HashMap<String, String> getHistory(){
		
		// read saved file object for search history
		Object stored = FileStuff.readObjectFile(_context, "history", false);
		
		// conditional statement to check for saved file 
		HashMap<String, String>history;
		if (stored == null){
			Log.i("HISTORY", "NO HISTORY FOUND");
			history = new HashMap<String, String>();
		} else {
			history = (HashMap<String, String>) stored;
		}
		return history;
	}
	
	// async to get url response in background
	private class QuoteRequest extends AsyncTask<URL, Void, String>{
		@Override
		protected String doInBackground(URL... urls){
			String response = "";
			for(URL url: urls){
				response = WebStuff.getURLStringResponse(url);
			}
			return response;
		}
		
		// check for json and parse
		@Override
		protected void onPostExecute(String result){
			Log.i("URL RESPONSE", result);
			try{
				// use json objects and drill down until desired data
				JSONObject json = new JSONObject(result);
				JSONObject results = json.getJSONObject("current_observation").getJSONObject("display_location");
				
				// SAVE history to object and string files
				_history.put(results.getString("elevation"), results.toString());
				FileStuff.storeObjectFile(_context, "history", _history, false);
				FileStuff.storeStringFile(_context, "temp", results.toString(), true);
				
				Log.i("test", _history.put(results.getString("elevation"), results.toString()));
				
				// use json to display geographic info
				ele.setText(_history.put(results.getString("elevation"), results.toString()));
				
				// read history from file and display
				showHist.setText(_history.toString());
				
			} catch (JSONException e){
				Log.e("JSON", "JSON OBJECT EXCEPTION");
			}
		}
	}
	
	

}
