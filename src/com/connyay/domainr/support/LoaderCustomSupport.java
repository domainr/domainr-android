package com.connyay.domainr.support;

import org.holoeverywhere.app.ListFragment;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.connyay.domainr.R;
import com.connyay.domainr.common.FlurryLogger;
import com.connyay.domainr.gson.Registrars;

/**
 * Demonstration of the implementation of a custom Loader.
 */
public class LoaderCustomSupport extends FragmentActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
	super.onCreate(savedInstanceState);
	savedInstanceState.getParcelableArray("regs");

	FragmentManager fm = getSupportFragmentManager();

	// Create the list fragment and add it as our sole content.
	if (fm.findFragmentById(android.R.id.content) == null) {
	    RegistrarListFragment list = new RegistrarListFragment();
	    fm.beginTransaction().add(android.R.id.content, list).commit();
	}
    }

    public static class RegistrarsAdapter extends ArrayAdapter<Registrars> {

	public RegistrarsAdapter(Context context) {
	    super(context, 0);
	}

	public View getView(final int position, View convertView,
		ViewGroup parent) {

	    LayoutInflater li = (LayoutInflater) getContext().getSystemService(
		    Context.LAYOUT_INFLATER_SERVICE);
	    convertView = li.inflate(R.layout.registrar, parent, false);

	    TextView name = (TextView) convertView
		    .findViewById(R.id.registrar_name);

	    Registrars registrar = getItem(position);
	    if (registrar != null) {
		name.setText(registrar.getName());

	    }

	    convertView.setOnClickListener(new View.OnClickListener() {
		public void onClick(View v) {

		    Registrars registrar = getItem(position);
		    Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri
			    .parse(registrar.getRegister_url()));
		    FlurryLogger.logDomainRegister(registrar.getRegister_url());
		    v.getContext().startActivity(browserIntent);
		}
	    });
	    return convertView;
	}

    }

    public static class RegistrarListFragment extends ListFragment {

	// This is the Adapter being used to display the list's data.
	RegistrarsAdapter mAdapter;
	Registrars[] regs;

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
	    super.onActivityCreated(savedInstanceState);

	    regs = (Registrars[]) getArguments().getParcelableArray("regs");
	    // Create an empty adapter we will use to display the loaded data.
	    mAdapter = new RegistrarsAdapter(getActivity());
	    setListAdapter(mAdapter);

	    // Start out with a progress indicator.
	    setListShown(false);
	    updateList(regs);
	}

	public void updateList(Registrars[] regs) {

	    for (int i = 0; i < regs.length; i++) {
		mAdapter.add(regs[i]);
	    }
	    mAdapter.notifyDataSetChanged();
	    setListShown(true);

	}

    }

}