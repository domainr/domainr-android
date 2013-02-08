package com.connyay.domainr.support;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.connyay.domainr.R;
import com.connyay.domainr.SingleView;
import com.connyay.domainr.common.FlurryLogger;
import com.connyay.domainr.gson.ResultsData;

@SuppressLint("ResourceAsColor")
public class ResultsAdapter extends ArrayAdapter<ResultsData> {

    public ResultsAdapter(Context context) {
	super(context, 0);
    }

    @SuppressWarnings("deprecation")
    public View getView(final int position, View convertView, ViewGroup parent) {

	LayoutInflater li = (LayoutInflater) getContext().getSystemService(
		Context.LAYOUT_INFLATER_SERVICE);
	convertView = li.inflate(R.layout.row, parent, false);

	TextView host = (TextView) convertView.findViewById(R.id.host);
	TextView subdomain = (TextView) convertView
		.findViewById(R.id.subdomain);
	TextView path = (TextView) convertView.findViewById(R.id.path);
	View available = convertView.findViewById(R.id.availability);

	ResultsData result = getItem(position);
	if (result != null) {
	    host.setText(result.getHost());
	    subdomain.setText(result.getSubdomain());
	    path.setText(result.getPath());

	    String availability = result.getAvailability();
	    if (availability.equals("available")) {
		available.setBackgroundDrawable(getContext().getResources()
			.getDrawable(R.drawable.available));
	    } else if (availability.equals("taken")) {
		available.setVisibility(View.INVISIBLE);

	    } else if (availability.equals("tld")
		    || availability.equals("unavailable")) {
		subdomain.setTextColor(getContext().getResources().getColor(
			R.color.domainr_dark_grey));
	    } else if (availability.equals("maybe")) {
		available.setBackgroundDrawable(getContext().getResources()
			.getDrawable(R.drawable.unknown));
	    }

	}

	convertView.setOnClickListener(new View.OnClickListener() {
	    public void onClick(View v) {

		ResultsData result = getItem(position);
		String selectedDomain = result.getDomain();
		Intent viewIntent = new Intent(getContext(), SingleView.class);
		viewIntent.putExtra("domain", selectedDomain);

		FlurryLogger.logSearchTap(selectedDomain);

		v.getContext().startActivity(viewIntent);
	    }
	});
	return convertView;
    }
}
