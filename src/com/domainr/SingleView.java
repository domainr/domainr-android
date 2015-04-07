package com.domainr;

import java.lang.Thread.UncaughtExceptionHandler;
import java.util.List;
import java.util.Vector;

import org.holoeverywhere.LayoutInflater;
import org.holoeverywhere.app.Activity;
import org.holoeverywhere.app.Fragment;
import org.holoeverywhere.widget.LinearLayout;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.LinearLayout.LayoutParams;
import android.widget.TextView;

import com.actionbarsherlock.view.MenuItem;
import com.androidquery.AQuery;
import com.androidquery.callback.AjaxCallback;
import com.androidquery.callback.AjaxStatus;
import com.domainr.common.FlurryLogger;
import com.domainr.gson.GsonTransformer;
import com.domainr.gson.Result;
import com.domainr.support.LoaderCustomSupport;
import com.viewpagerindicator.TitlePageIndicator;

/**
 * Demonstrates combining a TabHost with a ViewPager to implement a tab UI that
 * switches between tabs and also allows the user to perform horizontal flicks
 * to move between the tabs.
 */
public class SingleView extends Activity implements UncaughtExceptionHandler {
	private AQuery aq = new AQuery(this);
	long expire = 60 * 60 * 1000;

	ViewPager mViewPager;
	PagerAdapter mPagerAdapter;
	TitlePageIndicator mTitleIndicator;

	Result res;
	TextView singleDomain, singlePreAvail, singleAvail;

	String domain;

	@Override
	protected void onCreate(Bundle savedInstanceState) {

		super.onCreate(savedInstanceState);

		setContentView(R.layout.view);
		Intent intent = getIntent();
		domain = intent.getStringExtra("domain");
		getSupportActionBar().setDisplayHomeAsUpEnabled(true);

		if (savedInstanceState != null) {

			domain = savedInstanceState.getString("domain");
		}
		singleDomain = (TextView) findViewById(R.id.single_domain);
		singlePreAvail = (TextView) findViewById(R.id.single_pre_available);
		singleAvail = (TextView) findViewById(R.id.single_available);
		singleDomain.setText(domain);
		buildResult();
	}

	@Override
	protected void onSaveInstanceState(Bundle outState) {
		super.onSaveInstanceState(outState);

		outState.putString("domain", domain);
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case android.R.id.home:
			finish();
			return true;
		}

		return super.onOptionsItemSelected(item);
	}

	public class PagerAdapter extends FragmentPagerAdapter {
		protected String[] TITLES = new String[] { "Register", "More Info", };

		private List<Fragment> fragments;

		/**
		 * @param fm
		 * @param fragments
		 */
		public PagerAdapter(FragmentManager fm, List<Fragment> fragments) {
			super(fm);
			if (fragments.size() == 1)
				TITLES = new String[] { "More Info", };
			this.fragments = fragments;
		}

		/*
		 * (non-Javadoc)
		 * 
		 * @see android.support.v4.app.FragmentPagerAdapter#getItem(int)
		 */
		@Override
		public Fragment getItem(int position) {
			return this.fragments.get(position);
		}

		@Override
		public CharSequence getPageTitle(int position) {
			return this.TITLES[position];
		}

		/*
		 * (non-Javadoc)
		 * 
		 * @see android.support.v4.view.PagerAdapter#getCount()
		 */
		@Override
		public int getCount() {
			return this.fragments.size();
		}
	}

	public void buildResult() {

		String url = "https://api.domainr.com/v1/api/json/info?client_id=domainr-android&q="
				+ domain;
		GsonTransformer t = new GsonTransformer();

		aq.progress(R.id.singleProgress).transformer(t)
				.ajax(url, Result.class, expire, new AjaxCallback<Result>() {
					public void callback(String url, Result result,
							AjaxStatus status) {
						if (status.getCode() == 200) {
							// good response
							processResult(result);
						}

					}
				});
	}

	@SuppressWarnings("deprecation")
	public void processResult(Result result) {

		String availability = result.getAvailability();
		if (availability.equals("available")) {
			singleAvail.setTextColor(getResources().getColor(
					R.color.domainr_green));
			singleAvail.setText("Available!");
		} else if (availability.equals("taken")) {
			singlePreAvail.setText("This domain is ");
			singleAvail.setText("taken.");

			checkForSale();

		} else if (availability.equals("tld")
				|| availability.equals("unavailable")) {
			singleAvail.setTextColor(getResources().getColor(
					R.color.domainr_red));
			singleAvail.setText("Unavailable.");
		} else if (availability.equals("maybe")) {
			singleAvail.setTextColor(getResources().getColor(
					R.color.domainr_green));
			singleAvail.setText("Possibly Available.");
		}

		List<Fragment> fragments = new Vector<Fragment>();
		if (availability.equals("available") || availability.equals("maybe")) {
			Bundle a = new Bundle();
			a.putParcelableArray("regs", result.getRegistrars());
			fragments.add(Fragment.instantiate(this,
					LoaderCustomSupport.RegistrarListFragment.class.getName(),
					a));
		}

		Bundle b = new Bundle();
		b.putString("wiki", result.getTld().getWikipedia_url());
		b.putString("iana", result.getTld().getIana_url());
		b.putString("www", result.getWww_url());
		b.putString("whois", result.getWhois_url());
		b.putString("domain", result.getDomain());

		fragments.add(Fragment.instantiate(this,
				MoreInfoFragment.class.getName(), b));
		mPagerAdapter = new PagerAdapter(getSupportFragmentManager(), fragments);

		mViewPager = (ViewPager) findViewById(R.id.pager);
		mViewPager.setAdapter(mPagerAdapter);
		mTitleIndicator = (TitlePageIndicator) findViewById(R.id.titles);
		mTitleIndicator.setViewPager(mViewPager);

	}

	public void checkForSale() {

		LayoutInflater vi = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		View v = vi.inflate(R.layout.forsale, null);
		LinearLayout forSale = (LinearLayout) v.findViewById(R.id.forSale);

		LinearLayout ll = (LinearLayout) findViewById(R.id.domainTopElement);
		LayoutParams lp = new LayoutParams(LayoutParams.MATCH_PARENT,
				LayoutParams.WRAP_CONTENT);

		ll.addView(v, lp);
		forSale.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri
						.parse("https://domainr.com/" + domain + "/buy"));
				v.getContext().startActivity(browserIntent);

			}
		});
	}

	@Override
	public void uncaughtException(Thread thread, Throwable ex) {
		FlurryLogger.logUncaught(ex.getMessage());
	}
}
