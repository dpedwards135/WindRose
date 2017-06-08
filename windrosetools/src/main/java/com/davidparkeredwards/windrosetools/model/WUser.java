package com.davidparkeredwards.windrosetools.model;

import com.davidparkeredwards.windrosetools.wForm.WForm;

/**
 * Created by davidedwards on 6/8/17.
 */

public class WUser {
    private String name;
    private String userName;
    private String emailAddress;
    private String wUserId;
    private String authCurrentUserId; //-getUid()?

    //When user logs in, if it doesn't recognize their info it prompts them to create a WUser
    //Later I'll probably need to just incorporate all of this into the sign up screen and allow users to
    //create a separate username, etc, so they are not permanently tied to any given sign in method.


    public WUser(WForm wForm){};
}
