package com.experimental.tca.constant;

public interface UriConstant {

    interface AUTH{
        interface VERSION{
            interface ONE{
                String LOGIN = "/v1/auth/login";
            }

            interface TWO{
                String LOGIN = "/v1/auth/login";
            }
        }
    }
    interface EMP{
        interface VERSION{
            interface ONE{
               String CREATE_ACC = "/v1/emp/create/account";
               String GET_ALL = "/v1/emp/getAll";
               String REVOKE_ACC = "/v1/emp/revoke/account";
               String UPDATE_ACC = "/v1/emp/update/account";
            }

            interface TWO{
                String CREATE_ACC = "/v2/emp/create/account";
                String GET_ALL = "/v2/emp/getAll";
                String REVOKE_ACC = "/v2/emp/revoke/account";
                String UPDATE_ACC = "/v2/emp/update/account";
            }
        }
    }
}
