package com.youme.candid.youmeapp.Activity.utils;

public class URLs {

    public static final String ROOT_URL = "http://192.168.1.5/youme_app/api/v1/";
   // public static final String ROOT_URL = "http://candidtechnologies.co.in/youme_app/api/v1/";

    public static final String URL_REGISTER_STEP_1 = ROOT_URL + "register_step1"; //for register user detail
    public static final String URL_SIGN_IN = ROOT_URL + "login"; //for login

    public static final String URL_REGISTER_STEP_2 = ROOT_URL + "register_step2";//after register complete the step
    public static final String URL_GET_NAME_DETAIL = ROOT_URL + "get_user_name";//after register complete the step

    public static final String URL_UPDATE_AGE_GENDER = ROOT_URL + "update_age_and_gender";//for update user gender age
    //http://192.168.1.3/youme_app/api/v1/get_user_age_and_gender
    public static final String URL_GET_AGE_GENDER = ROOT_URL + "get_user_age_and_gender";//for get user gender age

    public static final String URL_AllUSERS = ROOT_URL + "all_user_list_under_distance";// to get all register users data

    public static final String URL_GET_CRUSH_BALANCE = ROOT_URL + "get_crush_balance";//for get user crush balance
    public static final String URL_GET_DATE_BALANCE = ROOT_URL + "get_dating_balance";//for get user dating balance


    //for send particular user mesage...
    public static final String URL_SEND_MESSAGE = ROOT_URL + "send_message";// to get all register users data
    public static final String URL_GET_MESSAGE = ROOT_URL + "get_message";// to get all register users data

    //for get particular user...
    public static final String URL_USER = ROOT_URL + "get_user";// to get user data
    public static final String URL_ANOTHER_USER_DETAIL = ROOT_URL + "get_other_user_details";// to get user data

    public static final String URL_PROFILE_LIKED = ROOT_URL + "like";//for like someones profile
    public static final String URL_PROFILE_DISLIKED = ROOT_URL + "dislike";//for dislike someones profile

    public static final String URL_PROFILE_ADD_FAV = ROOT_URL + "add_favorite_user";//for add user as favourite
    public static final String URL_SPAM_REPORTED= ROOT_URL + "reported_user";//for report of the user

    //for add the images to gallery
    public static final String URL_ADD_GALLERY= ROOT_URL + "add_gallery";

    //for change user password
    public static final String URL_CHANGEPASSWORD= ROOT_URL + "change_password";

    //for get gallery images of user
    public static final String URL_GALLERY_IMAGES= ROOT_URL + "get_gallery_image";

    //get all images of current user
    public static final String URL_All_GALLERY_IMAGES= ROOT_URL + "get_all_gallery_image";

    //for get all crush requests send by user
     public static final String URL_GET_SEND_CRUSHES = ROOT_URL + "get_sent_crushes_request";
    public static final String URL_SEND_CRUSH_REQUEST = ROOT_URL + "send_crush_request";//for send crush  request

    //for send user date request
    public static final String URL_SEND_DATE_REQ = ROOT_URL + "send_dating_request";

    //for get all date requests send by user
    public static final String URL_GET_SEND_DATE_REQ = ROOT_URL + "get_sent_dating_request";
    //for get all date receivesd requests send by user
    public static final String URL_GET_RECEIVED_DATE_REQ = ROOT_URL + "get_received_dating_request";

    //for get all favourites by user
    public static final String URL_GET_FAVOURITES = ROOT_URL + "get_all_favorited_user";

    //for get all receive crush requests send by user
    public static final String URL_GET_RECEIVED_CRUSH_REQUEST = ROOT_URL + "get_received_crushes_request";
    //for get all accepted crush by user
   // public static final String URL_ALL_ACCEPTED_CRUSH_USER = ROOT_URL + "get_all_accepted_crush_user";

    //for get all accepted crush req by user
    public static final String URL_ALL_ACCEPTED_DATE_USER = ROOT_URL + "get_all_accepted_dating_user";

    //for update  crush request
    //accept and reject
    public static final String URL_UPDATE_CRUSH_REQUEST = ROOT_URL + "update_crush_request";

    //for update  date request
    //accept and reject
    public static final String URL_UPDATE_DATE_REQUEST = ROOT_URL + "update_dating_request";

    //for update user location to find new user
    //near by current user
    public static final String URL_UPDATED_LOCATION = ROOT_URL + "upate_user_location";

    //for update user email...
     public static final String URL_UPDATE_USER_EMAIL = ROOT_URL + "update_email";

     //for update user zodiac
     public static final String URL_UPDATE_USER_ZODIAC = ROOT_URL + "update_zodiac_sign";
    public static final String URL_GET_USER_ZODIAC = ROOT_URL + "get_user_zodiac_sign";

    //for update user lifestyle
    public static final String URL_UPDATE_USER_LIFESTYLE = ROOT_URL + "update_life_style";
    public static final String URL_GET_USER_LIFESTYLE = ROOT_URL + "get_user_life_style";

    //for update user hobbies
    public static final String URL_UPDATE_USER_HOBBIES = ROOT_URL + "update_hobbies_interest";
    public static final String URL_GET_USER_HOBBIES = ROOT_URL + "get_user_hobbies_interest";

    //for get user profile pic
    public static final String URL_GET_USER_PROFILE_PIC = ROOT_URL + "get_user_profile_pic";
    public static final String URL_SET_USER_PROFILE_PIC = ROOT_URL + "add_user_profile_pic";//profile_pic

    public static final String URL_UPDATE_USER_PROFILE_PIC = ROOT_URL + "update_user_profile_pic";//
   // Param: user_id, old_gallery_image, new_gallery_image
   public static final String URL_DELETE_USER_GALLERY_PIC = ROOT_URL + "delete_gallery_image";//


    //for update user looking for
    public static final String URL_UPDATE_USER_LOKKINGFOR = ROOT_URL + "update_looking_for";
    public static final String URL_GET_USER_LOKKINGFOR = ROOT_URL + "get_user_looking_for";

    //for update user etjhenicity
    public static final String URL_UPDATE_USER_ETHENICITY = ROOT_URL + "update_ethnicity";
    public static final String URL_GET_USER_ETHENICITY = ROOT_URL + "get_zodiac_sign";

    //for update user education
    public static final String URL_UPDATE_USER_EDUCATION = ROOT_URL + "update_education";
    public static final String URL_GET_USER_EDUCATION = ROOT_URL + "get_zodiac_sign";

    //for update user Profession
    public static final String URL_UPDATE_USER_PROFESSION = ROOT_URL + "update_profession";

    //for update user apperance
    public static final String URL_UPDATE_USER_APPEARANCE = ROOT_URL + "update_appearance";
    public static final String URL_GET_USER_APPEARANCE = ROOT_URL + "get_user_appearance";

    //for update user nature
    public static final String URL_UPDATE_USER_NATURE = ROOT_URL + "update_nature";
    public static final String URL_GET_USER_NATURE = ROOT_URL + "get_user_nature";

    //for update compatibility test
    public static final String URL_UPDATE_COMPATIBILITY_TEST = ROOT_URL + "add_compatibility_test";
    public static final String URL_GET_COMPATIBILITY_TEST_RESULT = ROOT_URL + "get_user_compatibility_test_result";


    //for update relation stts
     public static final String URL_UPDATE_USER_RELATION_STATUS = ROOT_URL + "update_relationship_status";

    //send personality test data
    public static final String URL_UPDATE_USER_PERSONALITY_TEST = ROOT_URL + "add_personality_test";
    public static final String URL_GET_PERSONALITY_TEST_RESULT = ROOT_URL + "get_user_personality_test_result";

    //get packeges from server
    public static final String URL_GET_AVAILABLE_PACKAGES= ROOT_URL + "get_packages";
    public static final String URL_GET_MINIMUM_PACKAGES= ROOT_URL + "get_min_price_packages";

//...................................................UPDATION APIS//......................................................................
   //For Update Hide Age setting of user
   public static final String URL_HIDE_AGE_SETTING= ROOT_URL + "set_age_show";

    //For Update Hide Distance setting of user
    public static final String URL_HIDE_DISTANCE_SETTING= ROOT_URL + "set_distance_show";

    //For Update Show Mee setting of user
    public static final String URL_SHOW_ME_MEN_SETTING= ROOT_URL + "set_gender_male_show";

    //For Update Show WoMen setting of user
    public static final String URL_SHOW_ME_WOMEN_SETTING= ROOT_URL + "set_gender_female_show";

    //For Update Age Range setting of user
    public static final String URL_AGE_RANGE_SETTING= ROOT_URL + "set_age_range";

    //For Update Kms setting of user
    public static final String URL_KMS_SETTING= ROOT_URL + "set_distance_under";

    //..................................................for logout user........................
    public static final String URL_LOGOUT= ROOT_URL + "logout";

}
