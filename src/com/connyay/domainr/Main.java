package com.connyay.domainr;

import java.lang.Thread.UncaughtExceptionHandler;

import org.holoeverywhere.app.ListActivity;
import org.holoeverywhere.widget.Button;
import org.holoeverywhere.widget.EditText;
import org.holoeverywhere.widget.ListView;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.AbsListView;
import android.widget.AbsListView.OnScrollListener;
import android.widget.TextView.OnEditorActionListener;

import com.actionbarsherlock.view.Menu;
import com.actionbarsherlock.view.MenuInflater;
import com.actionbarsherlock.view.MenuItem;
import com.androidquery.AQuery;
import com.androidquery.callback.AjaxCallback;
import com.androidquery.callback.AjaxStatus;
import com.connyay.domainr.common.DelayedTextWatcher;
import com.connyay.domainr.common.FlurryLogger;
import com.connyay.domainr.gson.GsonTransformer;
import com.connyay.domainr.gson.Results;
import com.connyay.domainr.gson.ResultsData;
import com.connyay.domainr.support.ResultsAdapter;
import com.flurry.android.FlurryAgent;

public class Main extends ListActivity implements UncaughtExceptionHandler {
    ListView mainListView;
    EditText queryBox;
    Button clear;
    ResultsAdapter adapter;
    private AQuery aq;
    String currentSearch = "";

    // cache for an hour
    long expire = 60 * 60 * 1000;

    @Override
    public void onCreate(Bundle savedInstanceState) {
	super.onCreate(savedInstanceState);
	setContentView(R.layout.main);

	mainListView = getListView();

	queryBox = (EditText) findViewById(R.id.queryBox);
	adapter = new ResultsAdapter(this);
	mainListView.setAdapter(adapter);

	clear = (Button) findViewById(R.id.clear);

	queryBox.setOnEditorActionListener(new OnEditorActionListener() {

	    @Override
	    public boolean onEditorAction(android.widget.TextView v,
		    int actionId, KeyEvent event) {
		String search = queryBox.getText().toString()
			.replaceAll("\\s", "");
		if (actionId == EditorInfo.IME_ACTION_DONE
			&& !search.equals(currentSearch)) {
		    clearList();
		    buildResults(search);
		}
		return false;
	    }
	});

	clear.setOnClickListener(new OnClickListener() {

	    @Override
	    public void onClick(View v) {
		queryBox.setText("");
		clearList();
		InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
		imm.showSoftInput(queryBox, 0);
	    }
	});

	queryBox.addTextChangedListener(new DelayedTextWatcher(800) {
	    @Override
	    public void afterTextChangedDelayed(Editable s) {
		String search = s.toString();
		if (search.length() > 1 && !search.equals(currentSearch)) {
		    buildResults(s.toString().replaceAll("\\s", ""));
		}
	    }
	});

	mainListView.setOnScrollListener(new OnScrollListener() {
	    @Override
	    public void onScrollStateChanged(AbsListView view, int scrollState) {
		if (scrollState == SCROLL_STATE_FLING) {
		    InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
		    imm.hideSoftInputFromWindow(mainListView.getWindowToken(),
			    0);
		}
	    }

	    @Override
	    public void onScroll(AbsListView view, int firstVisibleItem,
		    int visibleItemCount, int totalItemCount) {
	    }
	});

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
	MenuInflater inflater = getSupportMenuInflater();
	inflater.inflate(R.menu.main, menu);
	return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
	switch (item.getItemId()) {
	case R.id.menu_about:
	    AboutDialog.newInstance().show(getSupportFragmentManager());
	    return true;
	case R.id.menu_share:
	    FlurryLogger.logDomainrShare();
	    String shareBody = "Check out Domainr: http://domai.nr";
	    Intent sharingIntent = new Intent(
		    android.content.Intent.ACTION_SEND);
	    sharingIntent.setType("text/plain");
	    sharingIntent
		    .putExtra(android.content.Intent.EXTRA_TEXT, shareBody);
	    startActivity(Intent.createChooser(sharingIntent, "Share Domai.nr"));
	    return true;
	}

	return super.onOptionsItemSelected(item);
    }

    public void buildResults(final String query) {
	aq = new AQuery(this);

	// log flurry search
	FlurryLogger.logSearch(query);

	// strip all whitespaces

	String url = "http://domai.nr/api/json/search?client_id=domainr-android&q="
		+ query;
	GsonTransformer t = new GsonTransformer();

	aq.progress(R.id.progress).transformer(t)
		.ajax(url, Results.class, expire, new AjaxCallback<Results>() {
		    public void callback(String url, Results results,
			    AjaxStatus status) {
			if (status.getCode() == 200) {
			    // good response
			    updateList(results, query);
			}
			if (status.getCode() == -101) {
			    // bad response
			    clearList();
			}

		    }
		});
    }

    public void updateList(Results results, String search) {
	// clear list first
	clearList();
	currentSearch = search;
	ResultsData[] resultsData = results.getResults();
	for (int i = 0; i < resultsData.length; i++) {
	    adapter.add(resultsData[i]);
	}

    }

    public void clearList() {
	currentSearch = "";
	adapter.clear();
	adapter.notifyDataSetChanged();
    }

    // FLURRY!
    @Override
    protected void onStart() {
	super.onStart();
	FlurryAgent.onStartSession(this, getString(R.string.flurry_api_key));
    }

    @Override
    protected void onStop() {
	super.onStop();
	FlurryAgent.onEndSession(this);
    }

    @Override
    public void uncaughtException(Thread thread, Throwable ex) {
	FlurryLogger.logUncaught(ex.getMessage());

    }

}
