package maps.google.simple;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesNotAvailableException;
import com.google.android.gms.common.GooglePlayServicesRepairableException;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.GoogleApiClient.ConnectionCallbacks;
import com.google.android.gms.common.api.GoogleApiClient.OnConnectionFailedListener;
import com.google.android.gms.location.places.Places;
import com.google.android.gms.location.places.ui.PlacePicker;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import android.os.Bundle;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

public class ActMain extends Activity implements OnMapReadyCallback, ConnectionCallbacks, OnConnectionFailedListener{
	
	private GoogleMap mvMap = null;
	private GoogleApiClient mGoogleApiClient;
	
	private void initUI() {		
		
		mvMap = ((MapFragment) getFragmentManager().findFragmentById(R.id.frMap)).getMap();
		mvMap.setMyLocationEnabled(true);
		mvMap.getUiSettings().setMyLocationButtonEnabled(false);
		mvMap.getUiSettings().setZoomControlsEnabled(true);
		mvMap.getUiSettings().setCompassEnabled(true);		
		mvMap.setBuildingsEnabled(true);
	}	

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);		
	
		mGoogleApiClient = new GoogleApiClient
	            .Builder(this)
	            .addApi(Places.GEO_DATA_API)
	            .addApi(Places.PLACE_DETECTION_API)
	            .addConnectionCallbacks(this)
	            .addOnConnectionFailedListener(this)
	            .build();
		
		setContentView(R.layout.act_main);
		this.initUI();
	}

	@Override
	protected void onStart() {
	    super.onStart();
	    mGoogleApiClient.connect();
	}

	@Override
	protected void onStop() {
	    mGoogleApiClient.disconnect();
	    super.onStop();
	}
	
	@Override	
	public boolean onCreateOptionsMenu(Menu menu) {			
		getMenuInflater().inflate(R.menu.act_main, menu);		
		return true;
	}	

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		if (item.getItemId() == R.id.menu_map_mode_normal) {			
			mvMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);
			return true;
		}
		if (item.getItemId() == R.id.menu_map_mode_satellite) {			
			mvMap.setMapType(GoogleMap.MAP_TYPE_SATELLITE);
			return true;
		}		
		if (item.getItemId() == R.id.menu_map_mode_terrain) {			
			mvMap.setMapType(GoogleMap.MAP_TYPE_TERRAIN);
			//mvMap.setTrafficEnabled(true);
			return true;
		}	
		if (item.getItemId() == R.id.menu_map_traffic) {									
			mvMap.setTrafficEnabled(!mvMap.isTrafficEnabled());			
			return true;			
		}
				
		if (item.getItemId() == R.id.menu_map_location) {														
			if (mvMap.isMyLocationEnabled()) { 
				LatLng target = new LatLng(mvMap.getMyLocation().getLatitude(), 
											mvMap.getMyLocation().getLongitude());		
				CameraUpdate camUpdate = CameraUpdateFactory.
						newLatLngZoom(target, 15F);
				mvMap.animateCamera(camUpdate);
			}			
			return true;			
		}	
		if (item.getItemId() == R.id.menu_map_point_new) {
			MarkerOptions marker = new MarkerOptions();
			marker.icon(null);
			marker.anchor(0.0f, 1.0f);
			LatLng target = new LatLng(mvMap.getMyLocation().getLatitude(), 
										mvMap.getMyLocation().getLongitude());		
			marker.title("My Location");
			marker.position(target);					
			mvMap.addMarker(marker);			
		}					
		if (item.getItemId() == R.id.act_settings) {
			Intent intent = new Intent(ActMain.this, SearchOptions.class);
			ActMain.this.startActivityForResult(intent, 1);
			
			int PLACE_PICKER_REQUEST = 1;
		    PlacePicker.IntentBuilder builder = new PlacePicker.IntentBuilder();

/*		    Context context = getApplicationContext();
		    try {
				startActivityForResult(builder.build(context), PLACE_PICKER_REQUEST);
			} catch (GooglePlayServicesRepairableException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (GooglePlayServicesNotAvailableException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
*/		}
		
		return super.onOptionsItemSelected(item);
		
	}
	
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		if (data == null) return;
		String search = data.getStringExtra("search");
		String place = "geo:"
							+mvMap.getMyLocation().getLatitude()
							+","
							+mvMap.getMyLocation().getLongitude()
							+"?q="
							+search.trim()+"&radius=1000";
		Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(place));
		this.startActivity(intent);
		Toast.makeText(getApplicationContext(), place, Toast.LENGTH_SHORT).show();
		
	//	Place place = PlacePicker.
	}

	@Override
	public void onMapReady(GoogleMap map) {
		
	}

	@Override
	public void onConnectionFailed(ConnectionResult arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onConnected(Bundle arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onConnectionSuspended(int arg0) {
		// TODO Auto-generated method stub
		
	}

}
