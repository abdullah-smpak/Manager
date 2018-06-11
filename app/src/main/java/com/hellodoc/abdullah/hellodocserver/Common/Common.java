package com.hellodoc.abdullah.hellodocserver.Common;

import com.hellodoc.abdullah.hellodocserver.Model.User;

public class Common {
    public static User currentuser;

    public static final String UPDATE = "Update";
    public static final String DELETE = "Delete";

    public static final int PICK_IMAGE_REQUEST = 71;

    public static String convertCodeToStatus(String code)
    {
        if(code.equals("0"))
            return "Pending";
        else if(code.equals("1"))
            return "Appointed";

        else
            return "Completed";
    }
}
