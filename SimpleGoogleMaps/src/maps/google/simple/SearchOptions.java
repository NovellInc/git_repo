package maps.google.simple;

import com.google.android.gms.common.GooglePlayServicesNotAvailableException;
import com.google.android.gms.common.GooglePlayServicesRepairableException;
import com.google.android.gms.location.places.ui.PlacePicker;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.view.View.OnClickListener;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;

public class SearchOptions extends Activity implements OnClickListener{
	
	EditText etSearch, etRadius;
	Button btnFind;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.search_options);
		
		etSearch = (EditText)findViewById(R.id.etSearch);
		etRadius = (EditText)findViewById(R.id.etRadius);
		btnFind = (Button)findViewById(R.id.btnFind);
		btnFind.setOnClickListener(this);
	}

	@Override
	public void onClick(View v) {
		Intent intent = new Intent();
	    intent.putExtra("search", etSearch.getText().toString().trim() + "&radius=" + etRadius.getText().toString().trim());
	    String search = etSearch.getText().toString();
	    
	    
	    
	    setResult(RESULT_OK, intent);
	    finish();
	}

}
