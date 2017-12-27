/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package arberdec26;

/**
 *
 * @author Lenovo
 */

    /*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

//CODE WORKS! It just got nuked during a shift in the indices from the json request. Once the indices are balanced you have to find
// where i put something in comments, if anything
// goals
// search all coins, only show arbable
// show volume top bids and asks
// calculate projected profit
//
// automatically perform trade



// Goals - make it work for more cryptos

/**
 *
 * @author Lenovo
 */
import java.awt.Toolkit;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.io.Reader;
import java.math.BigDecimal;
import java.net.URL;
import java.nio.charset.Charset;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Iterator;
import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.SourceDataLine;
import org.json.JSONArray;

import org.json.JSONException;
import org.json.JSONObject;

public class ArberDec26 {

  private static String readAll(Reader rd) throws IOException {
    StringBuilder sb = new StringBuilder();
    int cp;
    while ((cp = rd.read()) != -1) {
      sb.append((char) cp);
    }
    return sb.toString();
  }

  public static JSONObject readJsonFromUrl(String url) throws IOException, JSONException {
    InputStream is = new URL(url).openStream();
    try {
      BufferedReader rd = new BufferedReader(new InputStreamReader(is, Charset.forName("UTF-8")));
      String jsonText = readAll(rd);
      JSONObject json = new JSONObject(jsonText);
      
      
      return json;
    } finally {
      is.close();
    }
  }

  public static void main(String[] args) throws IOException, JSONException, InterruptedException, LineUnavailableException {
      
    //global parameters
    String cryptopiaPairs = "https://www.cryptopia.co.nz/api/GetCurrencies";
    String hitbtcPairs = "https://api.hitbtc.com/api/1/public/symbols";
    String odnURL;
    int beepDuration;
    int sleepDuraction;
    
    
      //First we are going to store ALL cryptopia pairs
      //WORKS FINE, REMOVE COMMENT BLOCK BELOW! FUCKING CRYPTOPIA WAS JUST DOWN, FUCKING PIECE OF SHIT
      
    // retrieves json of all coins. Grabs "data" json array. Runs lop to grab each index of the json array. prints each out
    JSONObject jsonObject1 = readJsonFromUrl("https://www.cryptopia.co.nz/api/GetCurrencies"); //THIS NEEDS TO BE IN A TRY-CATCH IN CASE IT DOESN'T CONNECT, WHICH HAS HAPPENED BEFORE
    JSONArray jsonArray1 = jsonObject1.getJSONArray("Data");
    /*for (int i = 0; i < jsonArray1.length(); i++) {
    JSONObject jsonAtIndex = jsonArray1.getJSONObject(i); //REENABLE THIS CODE -- moved to just after while loop x2
    System.out.println(jsonAtIndex.toString());
    }
    */
    
    JSONObject jsonObject2 = readJsonFromUrl("https://api.hitbtc.com/api/1/public/symbols"); //THIS NEEDS TO BE IN A TRY-CATCH IN CASE IT DOESN'T CONNECT, WHICH HAS HAPPENED BEFORE
    JSONArray jsonArray2 = jsonObject2.getJSONArray("symbols");
    for (int i = 0; i < jsonArray2.length(); i++) {
    JSONObject jsonAtIndex = jsonArray2.getJSONObject(i); //REENABLE THIS CODE
    System.out.println(jsonArray2.getJSONObject(i).get("commodity")+"/"+jsonArray2.getJSONObject(i).get("currency"));
    //System.out.println(jsonAtIndex.toString()); //shows all pairs on hitbtc
    }
    
    // bonus methods for testing-----
    System.out.println(jsonArray1.getJSONObject(5).get("Symbol")); //test method - prints out currency at index 5
    String coin = jsonArray1.getJSONObject(5).get("Symbol").toString(); // makes string of coin at index 5
    //String newAPIURL = "https://www.cryptopia.co.nz/api/GetMarketOrders/"+coin+"_BTC/1"; //makes url from string
    String newAPIURL = newAPIURL = constructCryptopiaUrl(coin);
    System.out.println("NEW URL EXAMPLE: "+newAPIURL);
    //----------------------------------
    
    //BONUS METHODS FOR TESTING -------------
    System.out.println(jsonArray2.getJSONObject(5).get("commodity"));
    coin = jsonArray2.getJSONObject(5).get("commodity").toString();
    //newAPIURL = "https://api.hitbtc.com/api/1/public/"+coin+"btc/orderbook"; //defunct - given own method at bottom
    newAPIURL = constructHitbtcUrl(coin);
    System.out.println("NEW URL EXAMPLE: "+newAPIURL);
    //-------------------------------
    
    String[] newURL1 = new String[jsonArray1.length()]; //initialising array of cryptopia urls before filling in for loop
    String[] newURL2 = new String[jsonArray2.length()]; //NOT IN USE YET
    
    for (int i = 0; i < jsonArray1.length(); i++) {
        
        String currency = jsonArray1.getJSONObject(i).get("Symbol").toString();
        newURL1[i] = constructCryptopiaUrl(currency);
        System.out.println(newURL1[i]);
    } 
    
    
      
    while(true) //loops the whole program
    {
        /*for (int i = 0; i < jsonArray1.length(); i++) { //THIS METHOD IS USED TO MAKE THE BIG MAIN METHOD CYCLE THROUGH AVAILABLE URLS
            bigMethod(newURL1[i]);
    } */
        
           bigMethod("https://www.cryptopia.co.nz/api/GetMarketOrders/odn_BTC/1"); 
    

    
    }
    
    
    
  }
  
 
public static void updateRecord(String input){
     PrintWriter pw = null;

  try {
     File file = new File("Arbitrage_Data.txt");
     FileWriter fw = new FileWriter(file, true);
     pw = new PrintWriter(fw);
     pw.println(input);
  } catch (IOException e) {
     e.printStackTrace();
  } finally {
     if (pw != null) {
        pw.close();
     }
  }
}

public static String constructCryptopiaUrl(String currency){
    String newAPIURL = "https://www.cryptopia.co.nz/api/GetMarketOrders/"+currency+"_BTC/1"; //makes url from string
    return newAPIURL;
}

public static String constructHitbtcUrl(String currency){
    String newAPIURL = "https://api.hitbtc.com/api/1/public/"+currency+"btc/orderbook";
    return newAPIURL; 
}

public static void beep(int durationInMs) throws LineUnavailableException{
    byte[] buf = new byte[ 1 ];;
    AudioFormat af = new AudioFormat( (float )44100, 8, 1, true, false );
    SourceDataLine sdl = AudioSystem.getSourceDataLine( af );
    sdl.open();
    sdl.start();
    for( int i = 0; i < 10 * (float )44100 / 1000; i++ ) {
        double angle = i / ( (float )44100 / 440 ) * 2.0 * Math.PI;
        buf[ 0 ] = (byte )( Math.sin( angle ) * 100 );
        sdl.write( buf, 0, 1 );
    }
    sdl.drain();
    sdl.stop();

}

public static void bigMethod(String URL) throws InterruptedException{
try{
        
        
    //this for loop is meant to be used with while loop to cycle through all currencies for arbitrage. not ready yet.
       
        
   // JSONObject jsonCryptopia = readJsonFromUrl("https://www.cryptopia.co.nz/api/GetMarketOrders/odn_BTC/1"); //----WORKS FINE, TEMPORARY SWAP
   JSONObject jsonCryptopia = readJsonFromUrl(URL);
    System.out.println("url: "+URL);
    //System.out.println(jsonCryptopia);
    
    
    JSONObject data = jsonCryptopia.getJSONObject("Data");
    String dataString = data.toString();
      //System.out.println("test:"+dataString);
    //JSONObject buy = data.getJSONArray("Buy");
    //JSONObject sell = data.getJSONObject("Sell");
    //System.out.println(data); // PRINTS ALL DATA
    
    //Print bidPrice
    
    String bid = data.get("Buy").toString().substring(10,18); //NEED TO FIX EXACT THINGIES 
    bid = bid.toString().substring(0, bid.indexOf("E")+3);
    float bidLong = Double.valueOf(bid).floatValue();
      
    float bidPrice = Float.parseFloat(bid);
    //System.out.println(BigDecimal.valueOf(bidPrice).toPlainString());
   // System.out.println("FLOAT bidprice: "+bidPrice);
    //print askPrice
    String ask = data.get("Sell").toString().substring(10,18);
    ask = ask.toString().substring(0, ask.indexOf("E")+3);
    float askPrice = Float.parseFloat(ask);
   // System.out.println("FLOAT askprice: "+askPrice);
    
    
    
    //now doing hitbtc
    
    JSONObject jsonHitbtc = readJsonFromUrl("https://api.hitbtc.com/api/1/public/odnbtc/orderbook");
    String dataString2 = jsonHitbtc.toString();
    String ask2 = dataString2.substring(dataString2.indexOf("[")+3, dataString2.indexOf(",")-1);
    String bid2 = dataString2.substring(dataString2.indexOf("d")+7,dataString2.indexOf("d")+15);
    float askPrice2 = Float.parseFloat(ask2);
    float bidPrice2 = Float.parseFloat(bid2);
    //System.out.println("Json hitbtc: "+dataString2);
    //System.out.println("ask hitbtc: "+ask2);
    //System.out.println("bid hitbtc: "+bid2);
    //System.out.println("askFloat hitbtc: "+askPrice2);
    //System.out.println("bidFloat hitbtc: "+bidPrice2);
    
    float percentProfit;
    
    //test sound
    
    
    
    
    DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
    Date date = new Date();
    String output;
    output = dateFormat.format(date);
    System.out.println(dateFormat.format(date)); //2016/11/16 12:08:43
    
    
    if(bidPrice>askPrice2){
        percentProfit = (bidPrice-askPrice2)/askPrice2*100;
        
        if(percentProfit>0.4){
        beep(50);
        }
    
        
    output = "CAN ARB: SELL "+bidPrice+" BUY "+askPrice2+" PROFIT "+percentProfit+"%";
        System.out.println(output);
    }
    else if(bidPrice2>askPrice){
        percentProfit = (bidPrice2-askPrice)/askPrice*100;
        if(percentProfit>0.4){
        beep(50);
        }
        //Toolkit.getDefaultToolkit().beep();
        
    output = "CAN ARB: SELL "+bidPrice2+" BUY "+askPrice+" PROFIT "+percentProfit+"%";
        System.out.println(output);
    }
    else{
        //Toolkit.getDefaultToolkit().beep();
        percentProfit = (bidPrice-askPrice2)/askPrice*100;
       output= "CAN'T ARB"+ " DIFFERENCE "+percentProfit+"% AND "+(bidPrice2-askPrice)/askPrice*100+"%";
        System.out.println(output);
    }
    updateRecord(output);
        System.out.println("Sleeping....................");
    Thread.sleep(2000);
    }
    
    catch(Exception e){
        System.out.println("Error/Bad connection");
        Thread.sleep(2000);
    
    }


}

}





