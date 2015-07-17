package com.example.aravindcm.cashondelivery;

import android.content.Context;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseFile;
import com.parse.ParseObject;
import com.parse.ParseQuery;

import java.util.List;

import it.gmariotti.cardslib.library.internal.CardExpand;

/**
 * Created by Aravind CM on 7/16/2015.
 */
public class CustomExpandCard extends CardExpand {
    int i;
    String pickup;
    String dropadd;

    //Use your resource ID for your inner layout
    public CustomExpandCard(Context context, int i) {
        super(context, R.layout.carddemo_example_inner_expand);
        this.i=i;
    }

    @Override
    public void setupInnerViewElements(ViewGroup parent, View view) {

        if (view == null) return;
        ParseQuery<ParseObject> query = ParseQuery.getQuery("packages");
        query.whereEqualTo("Booked", false);
        query.findInBackground(new FindCallback<ParseObject>() {
            public void done(List<ParseObject> packageList, ParseException e) {
                if (e == null) {
                    pickup="Warehouse : "+packageList.get(i).getString("Pickup");
                    dropadd="Drop address : "+packageList.get(i).getString("DropAdd");
                } else {
                    Log.d("Size", "Error: " + e.getMessage());
                }
            }
        });
        //Retrieve TextView elements
        TextView tx1 = (TextView) view.findViewById(R.id.carddemo_expand_text1);
        TextView tx2 = (TextView) view.findViewById(R.id.carddemo_expand_text2);
        //Set value in text views
        if (tx1 != null) {
            tx1.setText(pickup);
        }
        if(tx2 != null) {
            tx2.setText(dropadd);
        }

    }
}
