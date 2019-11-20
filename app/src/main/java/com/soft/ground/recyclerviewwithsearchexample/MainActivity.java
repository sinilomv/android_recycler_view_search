package com.soft.ground.recyclerviewwithsearchexample;

import android.app.SearchManager;
import android.content.Context;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements listAdapter.RecyclerViewOnClickListener {

    private Button btn;
    private RecyclerView listView = null;
    private TextView noMess = null;
    private listAdapter adapter;
    private List<MyObj> myObjList;
    private String[] players = {"player 1", "player 2", "player 3", "player 4", "player 5", "player 6", "player 7", "player 8", "player 9", "player 10", "player 11", "player 12", "player 13", "player 14", "player 15"};
    private boolean selectAll = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        listView = findViewById(R.id.recyclerView);
        noMess = findViewById(R.id.emptyList);

        //Set title and subtitle
        this.setTitle("View with Search");

        fillListView();

    }

    private void fillListView() {


        btn = findViewById(R.id.buttonOk);

        myObjList = new ArrayList<>();


        for (int i = 0; i < 13; i++) {

            MyObj myObj = new MyObj();

            myObj.setChecked(0);
            myObj.setTitle(players[i]);
            myObj.setID(i + 10);

            myObjList.add(myObj);
        }

        //BUTTON
        btn.setOnClickListener(arg0 -> Toast.makeText(this, "CLICK", Toast.LENGTH_LONG).show());

        if (!myObjList.isEmpty()) {

            adapter = new listAdapter(this);

            listView.setAdapter(adapter);

            adapter.setWarehouseList(myObjList);

            listView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));


        } else {

            noMess.setVisibility(View.VISIBLE);

        }

        btn.setEnabled(false);
        btn.setBackgroundResource(R.drawable.button_shape_inactive);

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        MenuInflater inflater = getMenuInflater();

        inflater.inflate(R.menu.menu, menu);

        SearchManager searchManager = (SearchManager) this.getSystemService(Context.SEARCH_SERVICE);

        SearchView searchView = (SearchView) menu.findItem(R.id.action_search)
                .getActionView();

        searchView.setSearchableInfo(searchManager
                .getSearchableInfo(this.getComponentName()));

        searchView.setMaxWidth(Integer.MAX_VALUE);

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {

            @Override
            public boolean onQueryTextSubmit(String query) {
                adapter.getFilter().filter(query);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String query) {
                adapter.getFilter().filter(query);
                return false;
            }
        });

        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {

            case R.id.select_all: {

                if (selectAll) {

                    for (MyObj myObj : myObjList) {

                        myObj.setChecked(0);
                    }

                    selectAll = false;

                } else {

                    for (MyObj myObj : myObjList) {

                        myObj.setChecked(1);
                    }

                    selectAll = true;
                }

                adapter.notifyDataSetChanged();

                break;
            }

            case R.id.action_search: {

                break;
            }
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {

        super.onBackPressed();
        this.finish();
    }

    @Override
    public void recyclerViewClick(int position, List<MyObj> myObjList, int param) {

        myObjList.get(position).setChecked(param);

        btn.setEnabled(false);
        btn.setBackgroundResource(R.drawable.button_shape_inactive);

        for (MyObj myObj : myObjList) {

            if (myObj.getChecked() == 1) {

                btn.setEnabled(true);
                btn.setBackgroundResource(R.drawable.button_shape_active);
            }

        }


    }
}
