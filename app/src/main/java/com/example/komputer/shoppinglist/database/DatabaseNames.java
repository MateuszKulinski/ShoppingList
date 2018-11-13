package com.example.komputer.shoppinglist.database;

import android.provider.BaseColumns;

public interface DatabaseNames extends BaseColumns {
    String DATABASE_NAME = "Shopping";
    int DATABASE_VERSION = 1;

    String LIST_PROD = "list_prod";
    String LIST_SHOP = "list_shop";

    String NAME_SHOP = "name_shop";
    String NAME_PROD = "name_prod";

    String ID_LIST = "id_shop";
    String ID_PROD = "id_prod";

    String QUANTITY_PROD = "quantity_prod";

    String PRICE_LIST = "total_price";
    String PRICE_PROD = "price_prod";

    String BOUGHT = "prod_buy";
}
