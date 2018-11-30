package com.youme.candid.youmeapp.Activity.utils;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;

import com.youme.candid.youmeapp.Activity.activity.SignUp;
import com.youme.candid.youmeapp.Activity.model.Nature;

import java.util.ArrayList;

public class SessionManager {

    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;
    Context context;
    int PRIVATE_MODE = 0;

    public static final String PREF_NAME = "YouAndMesharedpref";
    public static final String IS_LOGIN = "IsLoggedIn";

    public static final String IS_REGISTER= "IsRegisterd";
    public static final String IS_STEPONE= "IsStepone";
    public static final String IS_GENDER= "Isgender";

    public static final String IS_ZODIAC= "IsZodiac";
    public static final String IS_LIFESTYLE= "IsLifestyle";
    public static final String IS_HOBBY= "IsHobby";
    public static final String IS_UPLOADED= "IsUploaded";

    public static final String IS_LOOKINGFOR= "IsLookingfor";

    public static final String KEY_USER_ID = "userid";
    public static final String KEY_CHEKEDUSER_ID = "chekeduserid";

    public static final String KEY_ANOTHER_USER_ID = "anotheruserid";

    public static final String KEY_LONGITUDE = "longitude";
    public static final String KEY_LATITUDE = "latitude";

    public static final String KEY_FIRST_NAME = "keyfirstname";
    public static final String KEY_LAST_NAME = "keylastname";
    public static final String KEY_PSEUDONYM = "keypseudonym";
    public static final String KEY_EMAIL = "keyemail";
    public static final String KEY_USER_TYPE = "keyusertype";

    public static final String KEY_GENDER_ = "keygender";
    public static final String KEY_AGE   = "keyage";

    public static final String KEY_ZODIAC   = "keyzodiac";
    public static final String KEY_ZODIAC_Choosedpos   = "keychoosedpos";

    public static final String KEY_RELATIONSHIP_CHOOSED   = "keyrelationchoosedpos";
    public static final String KEY_RELATION_STATUS   = "keyrelationshipstts";

    public static final String KEY_LOOKINGFOR_CHOOSED   = "keylookingforchoosedpos";

    public static final String KEY_ETHENICITY_CHOOSED   = "keyethenicity";
    public static final String KEY_ETHENICITY   = "keynameethenicity";

    public static final String KEY_EDUCATION_CHOOSED   = "keyeducation";
    public static final String KEY_EDUCATION   = "keyeducationnm";

    public static final String KEY_PREVIOUS_POSITION   = "keypreviousposition";
    public static final String KEY_PREVIOUS_LOOKING_POSITION   = "keypreviouslookingposition";

    public static final String KEY_PROFESSION_CHOOSED    = "keyprofession";
    public static final String KEY_PROFESSION    = "keyprofessionnm";

    public static final String KEY_CRUSH_MIN_RANGE    = "keycrushmin";
    public static final String KEY_DATE_MIN_RANGE    = "keydatemin";
    public static final String KEY_COMBO_MIN_RANGE    = "keycombomin";


    public static final String KEY_LIFE_STYLE  = "keylifestyle";
    public static final String KEY_Position  = "keyposition";
    public static final String KEY_previousSelectedPosition0  = "keypreviousSelectedPosition0";
    public static final String KEY_previousSelectedPosition1  = "keypreviousSelectedPosition1";
    public static final String KEY_previousSelectedPosition2  = "keypreviousSelectedPosition2";
    public static final String KEY_previousSelectedPosition3  = "keypreviousSelectedPosition3";
    public static final String KEY_previousSelectedPosition4  = "keypreviousSelectedPosition4";
    public static final String KEY_previousSelectedPosition5  = "keypreviousSelectedPosition5";
    public static final String KEY_previousSelectedPosition6  = "keypreviousSelectedPosition6";
    public static final String KEY_previousSelectedPosition7  = "keypreviousSelectedPosition7";

    public static final String KEY_HOBBY  = "keyhobbies";
    public static final String KEY_POSITION_HOBBIES  = "keypositionhobbies";
    public static final String KEY_previousSelectedPositionH0  = "keypreviousSelectedPositionH0";
    public static final String KEY_previousSelectedPositionH1  = "keypreviousSelectedPositionH1";
    public static final String KEY_previousSelectedPositionH2  = "keypreviousSelectedPositionH2";
    public static final String KEY_previousSelectedPositionH3  = "keypreviousSelectedPositionH3";
    public static final String KEY_previousSelectedPositionH4  = "keypreviousSelectedPositionH4";
    public static final String KEY_previousSelectedPositionH5  = "keypreviousSelectedPositionH5";
    public static final String KEY_previousSelectedPositionH6  = "keypreviousSelectedPositionH6";
    public static final String KEY_previousSelectedPositionH7  = "keypreviousSelectedPositionH7";
    public static final String KEY_previousSelectedPositionH8  = "keypreviousSelectedPositionH8";
    public static final String KEY_previousSelectedPositionH9  = "keypreviousSelectedPositionH9";
    public static final String KEY_previousSelectedPositionH10  = "keypreviousSelectedPositionH10";
    public static final String KEY_previousSelectedPositionH11  = "keypreviousSelectedPositionH11";
    public static final String KEY_previousSelectedPositionH12  = "keypreviousSelectedPositionH12";
    public static final String KEY_previousSelectedPositionH13  = "keypreviousSelectedPositionH13";
    public static final String KEY_previousSelectedPositionH14  = "keypreviousSelectedPositionH14";
    public static final String KEY_previousSelectedPositionH15  = "keypreviousSelectedPositionH15";
    public static final String KEY_previousSelectedPositionH16  = "keypreviousSelectedPositionH16";
    public static final String KEY_previousSelectedPositionH17  = "keypreviousSelectedPositionH17";

    public static final String KEY_USER_WEIGHT  = "keyweight";
    public static final String KEY_USER_HEIGHT  = "keyheight";

    public static final String KEY_APPERANCE  = "keyapperance";
    public static final String KEY_POSITION_APPERANCE  = "keypositionapperance";
    public static final String KEY_previousSelectedPositionAP0  = "keypreviousSelectedPositiona0";
    public static final String KEY_previousSelectedPositionAP1  = "keypreviousSelectedPositiona1";
    public static final String KEY_previousSelectedPositionAP2  = "keypreviousSelectedPositiona2";
    public static final String KEY_previousSelectedPositionAP3  = "keypreviousSelectedPositiona3";
    public static final String KEY_previousSelectedPositionAP4  = "keypreviousSelectedPositiona4";
    public static final String KEY_previousSelectedPositionAp5  = "keypreviousSelectedPositiona5";

    public static final String KEY_NATURE  = "keynature";
    public static final String KEY_POSITION_NATURE  = "keypositionnature";
    public static final String KEY_previousSelectedPositionN0  = "keypreviousSelectedPositionN0";
    public static final String KEY_previousSelectedPositionN1  = "keypreviousSelectedPositionN1";
    public static final String KEY_previousSelectedPositionN2  = "keypreviousSelectedPositionN2";
    public static final String KEY_previousSelectedPositionN3  = "keypreviousSelectedPositionN3";
    public static final String KEY_previousSelectedPositionN4  = "keypreviousSelectedPositionN4";
    public static final String KEY_previousSelectedPositionN5  = "keypreviousSelectedPositionN5";
    public static final String KEY_previousSelectedPositionN6  = "keypreviousSelectedPositionN6";
    public static final String KEY_previousSelectedPositionN7  = "keypreviousSelectedPositionN7";
    public static final String KEY_previousSelectedPositionN8  = "keypreviousSelectedPositionN8";
    public static final String KEY_previousSelectedPositionN9  = "keypreviousSelectedPositionN9";
    public static final String KEY_previousSelectedPositionN10  = "keypreviousSelectedPositionN10";
    public static final String KEY_previousSelectedPositionN11  = "keypreviousSelectedPositionN11";
    public static final String KEY_previousSelectedPositionN12  = "keypreviousSelectedPositionN12";
    public static final String KEY_previousSelectedPositionN13  = "keypreviousSelectedPositionN13";
    public static final String KEY_previousSelectedPositionN14  = "keypreviousSelectedPositionN14";
    public static final String KEY_previousSelectedPositionN15  = "keypreviousSelectedPositionN15";
    public static final String KEY_previousSelectedPositionN16  = "keypreviousSelectedPositionN16";
    public static final String KEY_previousSelectedPositionN17  = "keypreviousSelectedPositionN17";
    public static final String KEY_previousSelectedPositionN18  = "keypreviousSelectedPositionN18";
    public static final String KEY_previousSelectedPositionN19  = "keypreviousSelectedPositionN19";


    public static final String KEY_MOBILE = "keymobile";
    public static final String KEY_PROFILE_IMAGE = "keyprofileimage";

    public static final String KEY_LOOKIN_FOR = "keylookingfor";

    public static final String KEY_AGE_HIDE_SWITCH = "keyagehideswitch";
    public static final String KEY_DISTANCE_HIDE_SWITCH = "keydistancehideswitch";

    public static final String KEY_SHOW_MEN_SWITCH = "keymenswitch";
    public static final String KEY_SHOW_WOMEN_SWITCH = "keywomeneswitch";
    public static final String KEY_SHOW_ME_TEXT = "keyshowme";

    public static final String KEY_RANGEKMS    = "keyselectedkms";

    public static final String KEY_MIN_RANGEAGE    = "keyminage";
    public static final String KEY_MAX_RANGEAGE    = "keymaxage";

    public static final String KEY_NEW_CRUSH_NOTIFY_SWITCH = "keynewcrushswitch";
    public static final String KEY_DATE_INVITION_NOTIFY_SWITCH = "keydateinvitionswitch";
    public static final String KEY_MESSAGE_NOTIFY_SWITCH = "keymessageswitch";
    public static final String KEY_DAILY_PACKS_NOTIFY_SWITCH = "keydailypacksswitch";
    public static final String KEY_OTHER_NOTIFY_SWITCH = "keyotherswitch";

    public static final String KEY_DIS_IN_KMS    = "keykms";
    public static final String KEY_DIS_IN_MILE    = "keymile";

    public static final String KEY_TOKEN = "token";

    public SessionManager(Context context) {
        this.context = context;
        sharedPreferences = context.getSharedPreferences(PREF_NAME, PRIVATE_MODE);
        editor = sharedPreferences.edit();

    }

    public void createRegistersession(String mobile,String user_id)
    {
        editor.putString(KEY_MOBILE, mobile);
        editor.putString(KEY_USER_ID,user_id);
        editor.putBoolean(IS_REGISTER, true);
        editor.commit();
    }

    public void createLoginsession(String mobile,String user_id)
    {
        editor.putString(KEY_MOBILE, mobile);
        editor.putString(KEY_USER_ID,user_id);
        editor.putBoolean(IS_LOGIN,true);
        editor.commit();
    }

    public void steponesession(String f_name, String l_name, String username)
    {
        editor.putString(KEY_FIRST_NAME, f_name);
        editor.putString(KEY_LAST_NAME,l_name);
        editor.putString(KEY_PSEUDONYM, username);
        editor.putBoolean(IS_STEPONE,true);
        editor.commit();

    }

    public void gender(String gender,String age)
    {
        editor.putString(KEY_GENDER_, gender);
        editor.putString(KEY_AGE,age);
        editor.putBoolean(IS_GENDER,true);
        editor.commit();

    }

    public void zodiacsign(String zodiac)
    {
        editor.putString(KEY_ZODIAC, zodiac);
        editor.putBoolean(IS_ZODIAC,true);
        editor.commit();

    }

    public void lifestyle(String lifestyle)
    {
        editor.putString(KEY_LIFE_STYLE, lifestyle);
        editor.putBoolean(IS_LIFESTYLE,true);
        editor.commit();

    }

    public void hobbies(String hobby)
    {
        editor.putString(KEY_HOBBY, hobby);
        editor.putBoolean(IS_HOBBY,true);
        editor.commit();

    }

    public void Uploaded(String imageString)
    {
        editor.putString(KEY_PROFILE_IMAGE, imageString);
        editor.putBoolean(IS_UPLOADED,true);
        editor.commit();

    }

    public void Lookingfor(String lookingfor)
    {
        editor.putString(KEY_LOOKIN_FOR, lookingfor);
        editor.putBoolean(IS_LOOKINGFOR,true);
        editor.commit();

    }

    public void logoutUser() {

     // editor.putBoolean(IS_LOGIN,false);
      // editor.putBoolean(IS_REGISTER,false);
        editor.clear();
        editor.commit();
        Intent i = new Intent(context, SignUp.class);
        i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
        i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(i);
    }

    public String getData(String key) {
        return sharedPreferences.getString(key, null);
    }

    public void setData(String key, String value) {
        editor.putString(key, value);
        editor.commit();
    }

    public boolean isRegisterdIn() {
        return sharedPreferences.getBoolean(IS_REGISTER, false);
    }

    public boolean isLoggedIn() {
        return sharedPreferences.getBoolean(IS_LOGIN, false);
    }

    public boolean isStepone() {
        return sharedPreferences.getBoolean(IS_STEPONE, false);
    }

    public boolean isGender() {
        return sharedPreferences.getBoolean(IS_GENDER, false);
    }

    public boolean isZodiac() {
        return sharedPreferences.getBoolean(IS_ZODIAC, false);
    }

    public boolean isLifestyle() {
        return sharedPreferences.getBoolean(IS_LIFESTYLE, false);
    }

    public boolean isHobby() {
        return sharedPreferences.getBoolean(IS_HOBBY, false);
    }

    public boolean isUploaded() {
        return sharedPreferences.getBoolean(IS_UPLOADED, false);
    }
    public boolean isLookingfor() {
        return sharedPreferences.getBoolean(IS_LOOKINGFOR, false);
    }

    //for personality test
    public static final String KEY_TEST_ONE = "keytestone";
    public static final String KEY_TEST_Two = "keytesttwo";
    public static final String KEY_TEST_THREE = "keytestthree";
    public static final String KEY_TEST_Four = "keytestfour";
    public static final String KEY_TEST_Five = "keytestfive";

    //data of compatibility test
    public static final String KEY_COMPATIBILITY_ONE  = "keycompatibilityone";
    public static final String KEY_COMPATIBILITY_TWO  = "keycompatibilitytwo";
    public static final String KEY_COMPATIBILITY_THREE  = "keycompatibilitythree";
    public static final String KEY_COMPATIBILITY_FOUR  = "keycompatibilityfour";
    public static final String KEY_COMPATIBILITY_FIVE  = "keycompatibilityfive";
    public static final String KEY_COMPATIBILITY_SIX  = "keycompatibilitysix";
    public static final String KEY_COMPATIBILITY_SEVEN  = "keycompatibilityseven";
    public static final String KEY_COMPATIBILITY_EIGHT  = "keycompatibilityeight";
    public static final String KEY_COMPATIBILITY_NINE  = "keycompatibilitynine";
    public static final String KEY_COMPATIBILITY_TEN  = "keycompatibilityten";

    public static final String KEY_COMPATIBILITY_ELEVEN  = "keycompatibilityeleven";
    public static final String KEY_COMPATIBILITY_TWELVE  = "keycompatibilitytwelve";
    public static final String KEY_COMPATIBILITY_THIRTEEN  = "keycompatibilitythrteen";
    public static final String KEY_COMPATIBILITY_FOURTEEN  = "keycompatibilityfouteen";
    public static final String KEY_COMPATIBILITY_FIFTEEN  = "keycompatibilityfifty";
    public static final String KEY_COMPATIBILITY_SIXTEEN  = "keycompatibilitysixty";
    public static final String KEY_COMPATIBILITY_SEVENTEEN  = "keycompatibilityseventy";
    public static final String KEY_COMPATIBILITY_EIGHTEEN  = "keycompatibilityeighty";
    public static final String KEY_COMPATIBILITY_NINTEEN = "keycompatibilityninty";
    public static final String KEY_COMPATIBILITY_TWENTY = "keycompatibilitytwenty";






}
