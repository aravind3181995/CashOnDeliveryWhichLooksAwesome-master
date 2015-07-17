package com.example.aravindcm.cashondelivery;

import android.content.Intent;
import android.os.Build;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseFile;
import com.parse.ParseObject;
import com.parse.ParseQuery;

import java.util.ArrayList;
import java.util.List;

import it.gmariotti.cardslib.library.internal.Card;
import it.gmariotti.cardslib.library.internal.CardArrayAdapter;
import it.gmariotti.cardslib.library.internal.CardExpand;
import it.gmariotti.cardslib.library.internal.CardHeader;
import it.gmariotti.cardslib.library.internal.CardThumbnail;
import it.gmariotti.cardslib.library.view.CardListView;


public class Packages extends ActionBarActivity {
    String[] description;
    ArrayList<Card> cards = new ArrayList<Card>();
    String[] links;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_packages);
        if (android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            getWindow().setNavigationBarColor(getResources().getColor(R.color.navigationBarColor));
        }
        ParseQuery<ParseObject> query = ParseQuery.getQuery("packages");
        query.whereEqualTo("Booked", false);
        query.findInBackground(new FindCallback<ParseObject>() {
            public void done(List<ParseObject> packageList, ParseException e) {
                if (e == null) {
                    Log.d("Size", "Retrieved " + packageList.size() + " packages");
                    description=new String[packageList.size()];
                    links=new String[packageList.size()];
                    for(int i=0;i<packageList.size();i++)
                    {
                        description[i]=packageList.get(i).getString("Description");
                        Log.e("cm", "" + description[i]);
                        ParseFile image=packageList.get(i).getParseFile("Image");
                        Log.e("cm",""+image);
                        links[i]=image.getUrl();
                        Log.e("cm",""+links[i]);
                    }
                } else {
                    Log.d("Size", "Error: " + e.getMessage());
                }
                displayfunc();
            }
        });

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_packages, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
    public void displayfunc()
    {
        for (int i = 0; i<description.length;i++) {
            // Create a Card
            Card card = new Card(this);
           // CardThumbnail thumb = new CardThumbnail(this);
            //thumb.setUrlResource(links[i]);
            card.setSwipeable(true);
            final int finalI = i;
            card.setOnLongClickListener(new Card.OnLongCardClickListener(){
                @Override
                public boolean onLongClick(Card card, View view) {
                    int index= finalI;
                    Toast.makeText(getApplicationContext(), "Clickable card", Toast.LENGTH_LONG).show();
                    Intent j =new Intent(Packages.this,Booking.class);
                    j.putExtra("index",index);
                    startActivity(j);
                    return false;
                }
            });
            // Create a CardHeader
            CardHeader header = new CardHeader(this);
            // Add Header to card
            header.setTitle(description[i]);
            card.setTitle("sample title");
            card.addCardHeader(header);
            header.setButtonExpandVisible(true);
            card.addCardHeader(header);
            card.setCardElevation(getResources().getDimension(R.dimen.card_elevation));

            //This provides a simple (and useless) expand area
            CustomExpandCard expand = new CustomExpandCard(this,i);
            //Add Expand Area to Card
            card.addCardExpand(expand);

            //Just an example to expand a card
            card.setExpanded(false);
            //card.addCardThumbnail(thumb);
            cards.add(card);


        }
        CardArrayAdapter mCardArrayAdapter = new CardArrayAdapter(this, cards);

        CardListView listView = (CardListView) this.findViewById(R.id.myList);
        if (listView != null) {
            listView.setAdapter(mCardArrayAdapter);
        }
    }
}
