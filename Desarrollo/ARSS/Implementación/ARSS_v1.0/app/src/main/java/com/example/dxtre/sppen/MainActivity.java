package com.example.dxtre.sppen;

import android.app.Activity;
import android.graphics.Color;
import android.os.StrictMode;
import android.support.annotation.NonNull;
import android.support.v4.view.ViewPager;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.example.dxtre.sppen.adapter.AdapterExtra;
import com.example.dxtre.sppen.adapter.AdapterHour;
import com.example.dxtre.sppen.adapter.AdapterLocation;
import com.example.dxtre.sppen.adapter.AdapterPagerImage;
import com.example.dxtre.sppen.adapter.AdapterSubService;
import com.example.dxtre.sppen.adapter.AdapterWayPay;
import com.example.dxtre.sppen.components.interfaces.OnExtra;
import com.example.dxtre.sppen.components.interfaces.OnSubService;
import com.example.dxtre.sppen.model.DateService;
import com.example.dxtre.sppen.model.Extra;
import com.example.dxtre.sppen.model.ImageService;
import com.example.dxtre.sppen.model.InfoService;
import com.example.dxtre.sppen.model.Item;
import com.example.dxtre.sppen.model.LocationService;
import com.example.dxtre.sppen.model.Parameter;
import com.example.dxtre.sppen.model.Service;
import com.example.dxtre.sppen.model.SubService;
import com.example.dxtre.sppen.model.User;
import com.example.dxtre.sppen.model.WayPay;
import com.example.dxtre.sppen.util.ApiHelper;
import com.example.dxtre.sppen.util.AsyncHttp;
import com.example.dxtre.sppen.util.interfaces.OnResponseHttp;
import com.prolificinteractive.materialcalendarview.CalendarDay;
import com.prolificinteractive.materialcalendarview.MaterialCalendarView;
import com.prolificinteractive.materialcalendarview.OnDateSelectedListener;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import dmax.dialog.SpotsDialog;

public class MainActivity extends Activity {

    private ImageView ivService;
    private TextView tvService;
    private TextView tvFavorite;
    private TextView tvNameClient;
    private TextView tvTotal;
    private TextView tvTimeAprox;
    private MaterialCalendarView mcCalendar;
    private ViewPager vpServiceDetail;

    private ListView lvHour;
    private ListView lvLocations;
    private GridView gvSubServices;
    private GridView gvWayPay;
    private ListView lvExtras;
    private Button btnReady;

    private AdapterSubService adapterSubService;
    private AdapterExtra adapterExtra;
    private AdapterHour adapterHour;
    private AdapterLocation adapterLocation;
    private AdapterWayPay adapterWayPay;

    private InfoService infoService;

    private int positionCalendar;

    private Item itemService;
    private Service service;

    private int total;
    private int time;

    private SpotsDialog dialog;
    private User user;

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);

        refresh();
    }

    public void refresh(){
        initializeObject();
        setupGUI();
        loadData();
        adapterHour = new AdapterHour(MainActivity.this, new ArrayList<String>());
        lvHour.setAdapter(adapterHour);
        processGetInfoService();
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    private void setupGUI(){

        ivService = (ImageView) findViewById(R.id.ivService);
        tvService = (TextView) findViewById(R.id.tvService);
        tvFavorite = (TextView) findViewById(R.id.tvFavorite);
        tvNameClient = (TextView) findViewById(R.id.tvName);
        tvTotal = (TextView) findViewById(R.id.tvTotal);
        tvTimeAprox = (TextView) findViewById(R.id.tvTimeAprox);
        mcCalendar = (MaterialCalendarView) findViewById(R.id.mcCalendar);
        lvHour = (ListView) findViewById(R.id.lvHour);
        lvLocations = (ListView) findViewById(R.id.lvLocations);
        gvSubServices = (GridView) findViewById(R.id.gvSubServices);
        gvWayPay = (GridView) findViewById(R.id.gvWayPay);
        vpServiceDetail = (ViewPager) findViewById(R.id.vpServiceDetail);
        lvExtras = (ListView) findViewById(R.id.lvExtras);
        btnReady = (Button) findViewById(R.id.btnReady);

        mcCalendar.clearSelection();

        tvFavorite.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                processAddFavorite();
            }
        });

        btnReady.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (adapterHour!=null && adapterHour.getSelected() != -1 && adapterWayPay.getSelected() != -1 &&
                        positionCalendar != -1 && adapterLocation.getSelected() != -1) {

                    OnResponseHttp onResponseHttp = new OnResponseHttp() {
                        @Override
                        public void onStart() {
                            dialog = new SpotsDialog(MainActivity.this);
                            dialog.show();
                        }

                        @Override
                        public void onSuccess(Object response) {
                            dialog.dismiss();
                            refresh();
                            ApiHelper.showToast(MainActivity.this, getString(R.string.strSuccess));
                        }

                        @Override
                        public void onFailure(Object response) {
                            dialog.dismiss();
                        }
                    };

                    ArrayList<Parameter> params = new ArrayList<>();

                    params.add(new Parameter("funcion", "getServiceDetail"));
                    params.add(new Parameter("id_user", String.valueOf(user.getIdUser())));
                    params.add(new Parameter("id_client_service", "2"));

                    AsyncHttp.post(onResponseHttp, params);


                } else
                    ApiHelper.showToast(MainActivity.this, getString(R.string.strEnterInput));
            }
        });

        lvHour.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                if (positionCalendar != -1) {
                    adapterHour.setSelected(position);

                    String hour = infoService.getLstDateServices().get(positionCalendar).getHours().get(position);

                    infoService.getLstDateServices().get(positionCalendar).getHours().remove(position);

                    infoService.getLstDateServices().get(positionCalendar).getHours().add(position, hour);

                    adapterHour.notifyDataSetChanged();
                }

            }
        });

        lvLocations.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                adapterLocation.setSelected(position);

                LocationService locationService = infoService.getLstLocationServices().get(position);

                infoService.getLstLocationServices().remove(position);
                infoService.getLstLocationServices().add(position, locationService);

                adapterLocation.notifyDataSetChanged();
            }
        });

        mcCalendar.setOnDateChangedListener(new OnDateSelectedListener() {
            @Override
            public void onDateSelected(@NonNull MaterialCalendarView widget, @NonNull CalendarDay date, boolean selected) {

                int pos = getDateServices(date);

                if (pos >= 0) {

                    if (infoService.getLstDateServices().get(pos).getHours()!=null
                            && infoService.getLstDateServices().get(pos).getHours().size()>0){
                        positionCalendar = pos;

                        adapterHour = new AdapterHour(MainActivity.this, infoService.getLstDateServices().get(pos).getHours());

                        lvHour.setAdapter(adapterHour);
                    } else {
                        adapterHour = new AdapterHour(MainActivity.this, new ArrayList<String>());
                        lvHour.setAdapter(adapterHour);
                        positionCalendar = -1;
                    }

                } else {
                    adapterHour = new AdapterHour(MainActivity.this, new ArrayList<String>());
                    lvHour.setAdapter(adapterHour);
                    positionCalendar = -1;
                }

            }
        });

        adapterLocation = new AdapterLocation(MainActivity.this, infoService.getLstLocationServices());
        lvLocations.setAdapter(adapterLocation);

        adapterWayPay = new AdapterWayPay(MainActivity.this, infoService.getLstWayPays());
        gvWayPay.setAdapter(adapterWayPay);

        gvWayPay.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                adapterWayPay.setSelected(position);

                WayPay wayPay = infoService.getLstWayPays().get(position);

                infoService.getLstWayPays().remove(wayPay);
                infoService.getLstWayPays().add(position, wayPay);

                adapterWayPay.notifyDataSetChanged();

            }
        });

        tvTotal.setText(String.format(getString(R.string.strTotalMxn), "0"));
        tvTimeAprox.setText(String.format(getString(R.string.strTotalAprox), "0"));

        btnReady.setVisibility(View.GONE);

    }

    private void initializeObject(){

        infoService = new InfoService();

        infoService.setLstLocationServices(new ArrayList<LocationService>());
        infoService.setLstDateServices(new ArrayList<DateService>());
        infoService.setLstWayPays(new ArrayList<WayPay>());

        infoService.getLstWayPays().add(new WayPay(1, R.drawable.ic_cash, R.string.strCash));
        infoService.getLstWayPays().add(new WayPay(2, R.drawable.ic_card1, R.string.strCard));
        infoService.getLstWayPays().add(new WayPay(3, R.drawable.ic_bank, R.string.strBank));
        infoService.getLstWayPays().add(new WayPay(4, R.drawable.ic_oxxo, R.string.strOxxo));

        user = ApiHelper.getUser();

        positionCalendar = -1;
    }

    private void loadData(){
        String nameService = "maquillaje";

        String image = "icon_"+nameService;

        image = ApiHelper.deAccent(image);

        int idImage = getResources().getIdentifier(image, "drawable", getPackageName());

        ivService.setImageResource(idImage);
        tvService.setText(nameService.toUpperCase());

        itemService = new Item();

        itemService.setColor("#B891C2");

        int serviceColor = Color.parseColor(itemService.getColor());

        ApiHelper.setBackGround(tvService, ApiHelper.createDrawableBackground(serviceColor));

    }

    private void processGetInfoService(){
        OnResponseHttp onResponseHttp = new OnResponseHttp() {
            @Override
            public void onStart() {
                dialog = new SpotsDialog(MainActivity.this);
                dialog.show();
            }

            @Override
            public void onSuccess(Object response) {

                ArrayList<LocationService> lstLocationServices = new ArrayList<>();
                ArrayList<DateService> lstDateServices = new ArrayList<>();
                ArrayList<ImageService> lstImages = new ArrayList<>();
                ArrayList<SubService> lstSubServices = new ArrayList<>();
                ArrayList<Extra> lstExtras = new ArrayList<>();

                try {
                    JSONObject jsonObject = (JSONObject) response;

                    if (jsonObject.getInt("state") == 200){

                        JSONObject data = jsonObject.getJSONObject("data");

                        JSONObject info = data.getJSONObject("info");

                        infoService.setIdService(info.getInt("id_service"));
                        infoService.setGrade(info.getString("grade"));
                        infoService.setLocationService(info.getString("location"));
                        infoService.setNameService(info.getString("name_service"));
                        infoService.setNameClient(info.getString("name"));
                        infoService.setTypeService(info.getInt("type_service"));

                        if (!data.isNull("image_gallery")) {
                            JSONArray images = data.getJSONArray("image_gallery");

                            for (int i=0; i<images.length(); i++) {

                                JSONObject image = images.getJSONObject(i);

                                ImageService imageService = new ImageService();

                                imageService.setId(image.getInt("id"));
                                imageService.setPhoto(image.getString("photo"));

                                lstImages.add(imageService);
                            }
                            infoService.setLstImageGallery(lstImages);
                        }

                        if (!data.isNull("sub_services")) {
                            JSONArray subServices = data.getJSONArray("sub_services");

                            for (int i=0; i<subServices.length(); i++) {
                                JSONObject subService = subServices.getJSONObject(i);

                                SubService service = new SubService();

                                service.setIdClientSubService(subService.getInt("id_client_subservice"));
                                service.setName(subService.getString("subservice_name"));
                                service.setCost(subService.getInt("cost"));
                                service.setTime(subService.getInt("time"));

                                lstSubServices.add(service);
                            }
                            infoService.setLstSubServices(lstSubServices);
                        }

                        if (!data.isNull("extras")) {
                            JSONArray extras = data.getJSONArray("extras");

                            for (int i=0; i<extras.length(); i++) {
                                JSONObject extra = extras.getJSONObject(i);

                                Extra service = new Extra();

                                service.setIdClientExtra(extra.getInt("id_client_extra"));
                                service.setName(extra.getString("extra_name"));
                                service.setCost(extra.getInt("cost"));
                                service.setTime(extra.getInt("time"));

                                lstExtras.add(service);
                            }
                            infoService.setLstExtras(lstExtras);
                        }

                        if (!data.isNull("schedule")) {
                            JSONArray schedules = data.getJSONArray("schedule");

                            for (int i=0; i<schedules.length(); i++) {

                                JSONObject schedule = schedules.getJSONObject(i);

                                JSONArray hours = schedule.getJSONArray("hours");

                                ArrayList<String> hoursTemp = new ArrayList<>();

                                for (int y=0; y<hours.length(); y++) {
                                    hoursTemp.add(hours.get(y).toString());
                                }

                                DateService dateService = new DateService(schedule.getInt("day"), schedule.getInt("month"), schedule.getInt("year"), hoursTemp);

                                lstDateServices.add(dateService);
                            }
                            infoService.getLstDateServices().addAll(lstDateServices);

                        }

                        if (!data.isNull("locations")) {
                            JSONArray locations = data.getJSONArray("locations");

                            for (int i=0; i<locations.length(); i++) {

                                JSONObject location = locations.getJSONObject(i);

                                LocationService locationService = new LocationService(location.getInt("id"), location.getString("location_name"));

                                lstLocationServices.add(locationService);

                            }
                            infoService.getLstLocationServices().addAll(lstLocationServices);
                        }

                        reloadData();
                    } else {
                        ApiHelper.showToast(MainActivity.this, jsonObject.getString("status_msg"));
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                } finally {
                    dialog.dismiss();
                }
            }

            @Override
            public void onFailure(Object response) {
                dialog.dismiss();
            }
        };

        ArrayList<Parameter> params = new ArrayList<>();

        params.add(new Parameter("funcion", "getServiceDetail"));
        params.add(new Parameter("id_user", String.valueOf(user.getIdUser())));
        params.add(new Parameter("id_client_service", "2"));

        AsyncHttp.post(onResponseHttp, params);
    }

    private void processAddFavorite(){
        OnResponseHttp onResponseHttp = new OnResponseHttp() {
            @Override
            public void onStart() {
                dialog = new SpotsDialog(MainActivity.this);
                dialog.show();
            }

            @Override
            public void onSuccess(Object response) {

                try {
                    JSONObject jsonObject = (JSONObject) response;

                    if (jsonObject.getInt("state") == 200){

                        ApiHelper.showToast(MainActivity.this, getString(R.string.strSuccessAddFavorite));

                    } else {
                        ApiHelper.showToast(MainActivity.this, jsonObject.getString("status_msg"));
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                } finally {
                    dialog.dismiss();
                }
            }

            @Override
            public void onFailure(Object response) {
                dialog.dismiss();
            }
        };

        ArrayList<Parameter> params = new ArrayList<>();

        params.add(new Parameter("funcion", "addFavorite"));
        params.add(new Parameter("id_user", String.valueOf(user.getIdUser())));
        params.add(new Parameter("id_client_service", String.valueOf("2")));

        AsyncHttp.post(onResponseHttp, params);
    }

    private void processSaveAppointment(){
        OnResponseHttp onResponseHttp = new OnResponseHttp() {
            @Override
            public void onStart() {
                dialog = new SpotsDialog(MainActivity.this);
                dialog.show();
            }

            @Override
            public void onSuccess(Object response) {

                try {
                    JSONObject jsonObject = (JSONObject) response;

                    if (jsonObject.getInt("state") == 200){

                        ApiHelper.showToast(MainActivity.this, getString(R.string.strSuccess));

                    } else {
                        ApiHelper.showToast(MainActivity.this, jsonObject.getString("status_msg"));
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                } finally {
                    dialog.dismiss();
                }
            }

            @Override
            public void onFailure(Object response) {
                dialog.dismiss();
            }
        };

        ArrayList<Parameter> params = new ArrayList<>();

        params.add(new Parameter("funcion", "saveAppointment"));
        params.add(new Parameter("id_user", String.valueOf(user.getIdUser())));
        params.add(new Parameter("id_client_service", String.valueOf(service.getIdService())));
        params.add(new Parameter("services", getSendSubServices()));
        params.add(new Parameter("extras", getSendExtra()));
        params.add(new Parameter("schedule", getSendSchedule()));
        params.add(new Parameter("id_payment",
                String.valueOf(infoService.getLstWayPays().get(adapterWayPay.getSelected()).getId())));

        String strDate = infoService.getLstDateServices().get(positionCalendar).getYear()
                + "-" + infoService.getLstDateServices().get(positionCalendar).getMonth()
                + "-" + infoService.getLstDateServices().get(positionCalendar).getDay();

        params.add(new Parameter("day_service", strDate));
        params.add(new Parameter("id_user_location",
                String.valueOf(infoService.getLstLocationServices().get(adapterLocation.getSelected()).getId())));

        calculeTotalAndTime();

        params.add(new Parameter("total", String.valueOf(total)));

        AsyncHttp.post(onResponseHttp, params);
    }

    private String getSendSchedule(){

        JSONArray jsonArraySchedule = new JSONArray();

        jsonArraySchedule.put(infoService.getLstDateServices().get(positionCalendar).getHours().get(adapterHour.getSelected()));

        return jsonArraySchedule.toString();
    }

    private void calculeTotalAndTime(){

        total = 0;
        time = 0;

        for (int i=0; i<infoService.getLstExtras().size(); i++) {

            Extra extra = infoService.getLstExtras().get(i);

            if (extra.getCount()>0) {
                time = time + (extra.getCount()*extra.getTime());
                total = total + (extra.getCount()*extra.getCost());
            }

        }

        for (int i=0; i<infoService.getLstSubServices().size(); i++) {

            SubService subService = infoService.getLstSubServices().get(i);

            if (subService.getCount()>0) {
                time = time + (subService.getCount()*subService.getTime());
                total = total + (subService.getCount()*subService.getCost());
            }

        }
    }

    private String getSendSubServices(){

        JSONArray jsonArrayServices = new JSONArray();

        for (int i=0; i<infoService.getLstSubServices().size(); i++) {

            SubService subService = infoService.getLstSubServices().get(i);

            if (subService.getCount()>0) {

                JSONObject jsonObject = new JSONObject();

                try {

                    jsonObject.put("id", subService.getIdClientSubService());
                    jsonObject.put("quantity", subService.getCount());

                } catch (JSONException e) {
                    e.printStackTrace();
                }

                jsonArrayServices.put(jsonObject);
            }

        }

        return jsonArrayServices.toString();
    }

    private String getSendExtra(){

        JSONArray jsonArrayExtras = new JSONArray();

        for (int i=0; i<infoService.getLstExtras().size(); i++) {

            Extra extra = infoService.getLstExtras().get(i);

            if (extra.getCount()>0) {

                JSONObject jsonObject = new JSONObject();

                try {

                    jsonObject.put("id", extra.getIdClientExtra());
                    jsonObject.put("quantity", extra.getCount());

                } catch (JSONException e) {
                    e.printStackTrace();
                }

                jsonArrayExtras.put(jsonObject);
            }

        }

        return jsonArrayExtras.toString();
    }

    private void reloadData(){

        btnReady.setVisibility(View.VISIBLE);

        adapterLocation.notifyDataSetChanged();

        tvNameClient.setText(infoService.getNameClient());

        if (infoService.getLstImageGallery() == null){
            infoService.setLstImageGallery(new ArrayList<ImageService>());
        }

        AdapterPagerImage adapterPagerImage = new AdapterPagerImage(MainActivity.this, infoService.getLstImageGallery());

        vpServiceDetail.setAdapter(adapterPagerImage);

        if (infoService.getLstSubServices() == null){
            infoService.setLstSubServices(new ArrayList<SubService>());
        }

        OnSubService onSubService = new OnSubService() {
            @Override
            public void count(int position) {

                SubService subService = infoService.getLstSubServices().get(position);
                subService.setCount(subService.getCount()+1);

                infoService.getLstSubServices().remove(position);
                infoService.getLstSubServices().add(position, subService);

                adapterSubService.notifyDataSetChanged();

                loadTotal();
            }

            @Override
            public void delete(int position) {
                SubService subService = infoService.getLstSubServices().get(position);
                subService.setCount(subService.getCount()-1);

                infoService.getLstSubServices().remove(position);
                infoService.getLstSubServices().add(position, subService);

                adapterSubService.notifyDataSetChanged();

                loadTotal();
            }
        };

        adapterSubService = new AdapterSubService(MainActivity.this,
                infoService.getLstSubServices(), onSubService, itemService);

        gvSubServices.setAdapter(adapterSubService);

        if (infoService.getLstExtras() == null) {
            infoService.setLstExtras(new ArrayList<Extra>());
        }

        OnExtra onExtra = new OnExtra() {
            @Override
            public void increase(int position) {
                Extra extra = infoService.getLstExtras().get(position);
                extra.setCount(extra.getCount()+1);

                infoService.getLstExtras().remove(position);
                infoService.getLstExtras().add(position, extra);

                adapterExtra.notifyDataSetChanged();

                loadTotal();
            }

            @Override
            public void decrease(int position) {
                Extra extra = infoService.getLstExtras().get(position);
                extra.setCount(extra.getCount()-1);

                infoService.getLstExtras().remove(position);
                infoService.getLstExtras().add(position, extra);

                adapterExtra.notifyDataSetChanged();

                loadTotal();
            }
        };

        adapterExtra = new AdapterExtra(MainActivity.this, infoService.getLstExtras(), onExtra);

        lvExtras.setAdapter(adapterExtra);
    }

    private int getDateServices(CalendarDay dateSearch) {

        for (int i=0; i<infoService.getLstDateServices().size(); i++) {

            DateService dateService = infoService.getLstDateServices().get(i);

            if (dateSearch.getYear() == dateService.getYear() &&
                    dateSearch.getMonth()+1 == dateService.getMonth() &&
                    dateSearch.getDay() == dateService.getDay()) {

                return i;

            }

        }

        return -1;
    }

    private void loadTotal(){

        calculeTotalAndTime();

        tvTotal.setText(String.format(getString(R.string.strTotalMxn), String.valueOf(total)));
        tvTimeAprox.setText(String.format(getString(R.string.strTotalAprox), String.valueOf(time)));
    }

    public Item getItemService() {
        return itemService;
    }

    public void setItemService(Item itemService) {
        this.itemService = itemService;
    }

    public Service getService() {
        return service;
    }

    public void setService(Service service) {
        this.service = service;
    }

}