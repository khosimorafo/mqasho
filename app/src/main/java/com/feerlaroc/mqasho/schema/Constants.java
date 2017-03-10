package com.feerlaroc.mqasho.schema;

/**
 * Created by root on 2016/03/17.
 */
public class Constants {


    public static final class ZOHO{

        public static final String TOKEN = "23d96588d022f48fe2ce16dfd2b69c71";
        public static final String ORG_ID = "163411778";
        public static final String VALUE = "value";
        public static final String INDEX = "index";

        public static final String PLACEHOLDER = "placeholder";
    }

    public static final class ZOHOLINEITEMSCHEMA {

        public static final String ITEM_ID = "item_id";
        public static final String SKU = "sku";
        public static final String NAME = "name";
        public static final String DESCRIPTION = "description";
        public static final String RATE = "rate";
        public static final String QUANTITY = "quantity";
        public static final String AMOUNT = "amount";
    }

    public static final class ZOHOINVOICESCHEMA {

        public static final String INVOICE_ID = "invoice_id";
        public static final String CUSTOMER_NAME = "customer_name";
        public static final String CUSTOMER_ID = "customer_id";
        public static final String INVOICE_NUMBER = "invoice_number";
        public static final String DESCRIPTION = "description";
        public static final String INVOICE_AMOUNT = "total";
        public static final String OUTSTANDING_AMOUNT = "balance";
        public static final String STATUS = "status";

        public static final String DATE = "date";
        public static final String CREATED_DATE = "created_time";
        public static final String DUE_DATE = "due_date";
        public static final String DUE_DAYS = "due_days";
        public static final String BALANCE = "balance";
    }

    public static final class ZOHOITEMSCHEMA {

        public static final String ITEM_ID = "item_id";
        public static final String ITEM_NAME = "name";
        public static final String SKU = "sku";
        public static final String RATE = "rate";
        public static final String DESCRIPTION = "description";
        public static final String STATUS = "status";
    }

    public static final class ZOHOCONTACTSCHEMA {

        public static final String CUSTOMER_NAME = "contact_name";
        public static final String COMPANY_NAME = "company_name";
        public static final String CUSTOMER_ID = "contact_id";
        public static final String OUTSTANDING_AMOUNT = "outstanding_receivable_amount";
        public static final String UNUSED_CREDIT_AMOUNT = "unused_credits_receivable_amount";

        public static final String FIRST_NAME = "first_name";
        public static final String LAST_NAME = "last_name";
        public static final String ZAID = "zaid";
        public static final String MOBILE = "mobile";
        public static final String TELEPHONE = "phone";
        public static final String EMAIL = "email";
        public static final String IMAGE_URL = "image_url";
        public static final String CONTACT_PERSONS = "contact_persons";
        public static final String CUSTOM_FIELDS = "custom_fields";
        public static final String ZARIDNO_PLACE_HOLDER = "cf_zar_id_no";
    }

    public static final class ZOHOPAYMENTSCHEMA {

        public static final String CUSTOMER_NAME = "customer_name";
        public static final String CUSTOMER_ID = "customer_id";

        public static final String INVOICES = "invoices";
        public static final String INVOICE_ID = "invoice_id";
        public static final String INVOICE_NUMBER = "invoice_number";
        public static final String AMOUNT_APPLIED_TO_INVOICE = "amount_applied";
        public static final String TAX_AMOUNT_WITHHELD = "tax_amount_withheld";

        public static final String DESCRIPTION = "description";
        public static final String PAYMENT_AMOUNT = "amount";
        public static final String PAYMENT_DATE = "date";
        public static final String DATE = "date";
        public static final String REFERENCE_NUMBER = "reference_number";
        public static final String EXCHANGE_RATE = "exchange_rate";
        public static final String BANK_CHARGES = "bank_charges";
        public static final String PAYMENT_MODE = "payment_mode";

        public static final String MODE_CASH = "Cash";
        public static final String MODE_CARD = "Card";
        public static final String MODE_EFT = "EFT";
        public static final String MODE_MONEY_MARKET = "MoneyMarket";


    }

    public static final class FEERLAROC {

        public static final String EMPTY_STRING = "";

        public static final String API = "api";
        /*Denote Feerlaroc Rest API Version*/
        public static final String VERSION = "v.2";

        public static final String CUSTOMERS = "customers";
        public static final String INVOICES = "invoices";
        public static final String MONTHLIES = "monthlies";
        public static final String LINEITEMS = "line_items";
        public static final String ITEMS = "items";
        public static final String TENANTS = "tenants";

        public static final String CUSTOMER = "customer";
        public static final String INVOICE = "invoice";
        public static final String MONTHLY = "monthly";
        public static final String LINEITEM = "line_item";


        public static final String ITEM = "item";
        public static final String CUSTOMERPAYMENT = "payment";
        public static final String TENANT = "tenant";
        public static final String ENTRY = "entry";
        public static final String CUSTOMER_ID = "customer_id";
        public static final String INVOICE_ID = "invoice_id";
        public static final String CONTACT_ID = "contact_id";
        public static final String ESTIMATE_ID = "estimate_id";
        public static final String PAYMENT_ID = "payment_id";
        public static final String ITEM_ID = "item_id";
        public static final String GENERIC_AMOUNT = "amount";
        public static final String APPLIED_AMOUNT = "applied_amount";

        public static final String DATE_ADDED = "date_added";
        public static final String DATE_LAST_UPDATE = "date_last_update";

        public static final String PROPERTIES = "properties";
        public static final String PROPERTY = "property";

        public static final String LOCATION_REFERENCE = "parent_ref";
        public static final String PARENT = "parent";


        public static final String ITEMS2 = "items2";

        public static final String ACTUAL_DATE = "actual_date";
        public static final String FISCAL_PERIOD = "fiscal_period";
        public static final String FISCAL_MONTH = "fiscal_month";
        public static final String FISCAL_YEAR = "fiscal_year";
        public static final String CALENDER_MONTH = "calender_month";
        public static final String CALENDER_YEAR = "calender_year";

        public static final String PAYMENTS = "payments";
        public static final String PAYMENT = "payment";

        public static final String DATE = "date";
        public static final String UNCONFIRMED = "unconfirmed";
    }

    public static final class ITEMSCHEMA {

        public static final String ITEM_ID = "id";
        public static final String NAME = "name";
        public static final String DESCRIPTION = "description";
        public static final String RATE = "rate";
        public static final String STATUS = "status";
        public static final String SKU = "sku";
        public static final String IMAGE_URL = "image_url";
        public static final String CATEGORY = "category";
    }

    public static final class CUSTOMERSCHEMA {

        public static final String CUSTOMER_ID = "id";
        public static final String NAME = "name";
        public static final String ZAID = "zaid";
        public static final String CONTACT = "contact_person";
        public static final String MOBILE = "mobile";
        public static final String TELEPHONE = "telephone";
        public static final String FAX = "fax";
        public static final String EMAIL = "email";
        public static final String IMAGE_URL = "image_url";
        public static final String CATEGORY = "category";

        public static final String CONFIGURATION = "config";

        public static final String OUTSTANDING_AMOUNT = "outstanding_invoices_amount";
        public static final String OVERDUE_AMOUNT = "overdue_invoices_amount";
        public static final String UNUSED_CREDIT_AMOUNT = "unused_credit_amount";
        public static final String INVOICES = "invoices";
        public static final String CUSTOMER_INVOICES = "customer_invoices";
        public static final String MONTHLIES = "invoices";

    }

    public static final class  MONTHLYSCHEMA {

        public static final String MONTHLY_ID = "monthly_id";
        public static final String MONTHLY_NAME = "monthly_name";
        public static final String CUSTOMER_ID = "customer_id";
        public static final String PAYMENT_DATE = "payment_date";
        public static final String DUE_DATE = "due_date";
        public static final String DATEDUE_NETT = "datedue_nett";
        public static final String CREATED_DATE = "created_date";
        public static final String LAST_MODIFIED_DATE = "last_modified_date";
        public static final String STATUS = "status";
        public static final String PAID_AMOUNT =  "paid_amount";
        public static final String DUE_AMOUNT =  "due_amount";

        public static final String PAYMENTS = "payments";
    }

    public static final class  INVOICESCHEMA {

        public static final String INVOICE_ID = "invoice_id";
        public static final String CUSTOMER_ID = "customer_id";
        public static final String INVOICE_NUMBER = "invoice_number";
        public static final String DESCRIPTION = "description";
        public static final String PAID_AMOUNT = "invoice_paid_amount";
        public static final String INVOICE_AMOUNT = "invoice_amount";
        public static final String OUTSTANDING_AMOUNT = "outstanding_amount";
        public static final String LINEITEMS = "line_items";
        public static final String PAYMENTS = "payments";
        public static final String INVOICE_DATE = "invoice_date";
        public static final String ACTUAL_DATE = "actual_date";
        public static final String DUE_DATE = "due_date";
        public static final String DATEDUE_NETT = "datedue_nett";
        public static final String CREATED_DATE = "created_date";
        public static final String LAST_MODIFIED_DATE = "last_modified_date";
        public static final String STATUS = "status";

        public static final String AMOUNT_PAID =  "invoice_paid_amount";;
        public static final String INVOICE_LINE_ITEMS = "invoice_line_items";

        public static final String RECURRING_NAME = "recurrence_name";
        public static final String RECURRING_START_DATE = "start_date";
        public static final String RECURRING_END_DATE = "end_date";
        public static final String RECURRING_FREQUENCY = "recurrence_frequency";
        public static final String RECURRING_REPEAT_EVERY = "repeat_every";


    }

    public static final class INVOICEITEMSCHEMA {

        public static final String SKU = "id";
        public static final String NAME = "name";
        public static final String DESCRIPTION = "description";
        public static final String RATE = "rate";
        public static final String QUANTITY = "quantity";
        public static final String AMOUNT = "amount";
    }

    public static final class  PAYMENTSCHEMA {

        public static final String INVOICE_ID = "invoice_id";
        public static final String CUSTOMER_ID = "customer_id";
        public static final String INVOICES = "invoices";
        public static final String PAYMENT_NUMBER = "payment_number";
        public static final String REFERENCE = "description";
        public static final String PAYMENT_DATE = "payment_date";
        public static final String AMOUNT_PAID = "amount_paid";
        public static final String CREATED_DATE = "created_date";
        public static final String LAST_MODIFIED_DATE = "last_modified_date";
        public static final String PAYMENT_LINE_AMOUNT = "payment_line_amount";
        public static final String IS_SINGLE_PAYMENT_INVOICE = "is_single_invoice_payment";
        public static final String MODE = "comment";
        public static final String COMMENT = "comment";
    }

    public static final class  TENANTSCHEMA {

        public static final String CUSTOMER_ID = "customer_id";
        public static final String ITEM_ID = "item_id";
        public static final String FIRST_PAYMENT_DATE = "first_payment_date";
        public static final String MONTHLY_PAYMENT_DATE = "monthly_payment_date";
        public static final String STATUS = "status";
    }

    public static final class STATUS {

        //Used for yet to be saved entities.
        public static final String UNCONFIRMED = "unconfirmed";

        //Invoices
        public static final String PAID = "paid";
        public static final String UNPAID = "unpaid";
        public static final String DRAFT = "draft";
        public static final String PARTIALLYPAID = "partial";
        public static final String SENT = "sent";
        public static final String OVERDUE = "overdue";
        public static final String VOID = "void";
        public static final String UNKNOWN = "unknown";

        //Items
        public static final String ACTIVE = "active";
        public static final String INACTIVE = "inactive";
        public static final String SUSPENDED = "suspended";
        public static final String DISCONTINUED = "discontinued";

    }

}
