package com.bitsco.vks.sothuly.constant;

public interface SoThuLyConstant {
    interface TABLE {
        public static final String DENOUNCE_DENOUNCEMENT = "DENOUNCE_DENOUNCEMENT";
        public static final String DENOUNCE_SETTLEMENT_DECISION = "DENOUNCE_SETTLEMENT_DECISION";
        public static final String DENOUNCE_VERIFICATION_INVESTIGATION = "DENOUNCE_VERIFICATION_INVESTIGATION";
        public static final String DENOUNCE_INVESTIGATION_ACTIVITY = "DENOUNCE_INVESTIGATION_ACTIVITY";
        public static final String DENOUNCE_DENOUNCED_PERSON = "DENOUNCE_DENOUNCED_PERSON";
        public static final String IDENTIFIERS_DENOUNCE = "IDENTIFIERS_DENOUNCE";
        public static final String REGISTER_DECISION = "REGISTER_DECISION";
        public static final String REGISTER_DECISION_NUM = "REGISTER_DECISION_NUM";
        public static final String REVIEW_CASE = "REVIEW_CASE";
        public static final String REVIEW_CASE_ACCUSED = "REVIEW_CASE_ACCUSED";
        public static final String REVIEW_CASE_REQUEST = "REVIEW_CASE_REQUEST";
        public static final String FEEDBACK = "FEEDBACK";
        public static final String AP_PARAM = "AP_PARAM";
        public static final String ARREST_DETENTION_INFO = "ARREST_DETENTION_INFO";
        public static final String ARREST_DETENTION_ARRESTEE = "ARREST_DETENTION_ARRESTEE";
        public static final String ARREST_DETENTION_LAW_OFFENSE = "ARREST_DETENTION_LAW_OFFENSE";
        public static final String ARREST_DETENTION_SETTLEMENT_DECISION = "ARREST_DETENTION_SETTLEMENT_DECISION";
        public static final String ARREST_DETENTION_DISCIPLINE_VIOLATION = "ARREST_DETENTION_DISCIPLINE_VIOLATION";

        public static final String VIOLATION_LAW = "VIOLATION_LAW";
        public static final String VIOLATION_LEGISLATION_DOCUMENT = "VIOLATION_LEGISLATION_DOCUMENT";
        public static final String VIOLATION_RESULT = "VIOLATION_RESULT";

        public static final String COMPENSATION = "COMPENSATION";
        public static final String COMPENSATION_DOCUMENT = "COMPENSATION_DOCUMENT";
        public static final String COMPENSATION_DETAIL = "COMPENSATION_DETAIL";
        public static final String COMPENSATION_DAMAGE = "COMPENSATION_DAMAGE";

        public static final String COMMENT = "COMMENTS";
    }

    interface SEQUENCE {
        public static final String DENOUNCEMENT_ID_SEQ = "DENOUNCEMENT_ID_SEQ";
        public static final String DENOUNCED_PERSON_ID_SEQ = "DENOUNCED_PERSON_ID_SEQ";
        public static final String IDENTIFIERS_DENOUNCE_SEQ = "IDENTIFIERS_DENOUNCE_SEQ";
        public static final String SQ_REGISTER_DECISION = "SQ_REGISTER_DECISION";
        public static final String SQ_REGISTER_DECISION_NUM = "SQ_REGISTER_DECISION_NUM";
        public static final String SQ_REVIEW_CASE = "SQ_REVIEW_CASE";
        public static final String SQ_REVIEW_CASE_ACCUSED = "SQ_REVIEW_CASE_ACCUSED";
        public static final String SQ_REVIEW_CASE_REQUEST = "SQ_REVIEW_CASE_REQUEST";
        public static final String SQ_ARREST_DETENTION_ARRESTEE = "SQ_ARREST_DETENTION_ARRESTEE";
        public static final String SQ_ARREST_DETENTION_SETTLEMENT_DECISION = "SQ_SETTLEMENT_DECISION";
        public static final String SQ_ARREST_DETENTION_INFO = "SQ_ARREST_DETENTION_INFO";
        public static final String SQ_ARREST_DETENTION_LAW_OFFENSE = "SQ_LAW_OFFENSE";
        public static final String SQ_ARREST_DETENTION_DISCIPLINE_VIOLATION = "SQ_ARREST_DETENTION_DISCIPLINE_VIOLATION";
        public static final String SQ_FEEDBACK = "SQ_FEEDBACK";
        public static final String SQ_COMMENT = "SQ_COMMENT";
        public static final String SQ_VIOLATION_LAW= "SQ_VIOLATION_LAW";
        public static final String SQ_VIOLATION_LEGISLATION_DOCUMENT= "SQ_VIOLATION_LEGISLATION_DOCUMENT";
        public static final String SQ_VIOLATION_RESULT = "SQ_VIOLATION_RESULT";
        public static final String SQ_COMPENSATION = "SQ_COMPENSATION";
        public static final String SQ_COMPENSATION_DOCUMENT = "SQ_COMPENSATION_DOCUMENT";
        public static final String SQ_COMPENSATION_DETAIL = "SQ_COMPENSATION_DETAIL";
    }


    interface PACKAGE {
        public static final String PKG_REGISTER_DECISION = "PKG_REGISTER_DECISION";
        public static final String PKG_REVIEW_CASE = "PKG_REVIEW_CASE";
        public static final String PKG_ARREST_DETENTION_INFO = "PKG_ARREST_DETENTION_INFO";
        public static final String PKG_ARRESTEE_DETENTION_INFOR_PACKAGE = "PKG_ARRESTEE_DETENTION_INFOR_PACKAGE";
        public static final String PKG_ARREST_DETENTION_ARRESTEE = "PKG_ARREST_DETENTION_ARRESTEE";
        public static final String PKG_DENOUNCE_DENOUNCEMENT = "PKG_DENOUNCE_DENOUNCEMENT";
        public static final String PKG_VIOLATION = "PKG_VIOLATION";
        public static final String PKG_COMPENSATION = "PKG_COMPENSATION";
    }

    interface FUNCTION {
        public static final String GET_LIST_REGISTER_DECISION = "GET_LIST_REGISTER_DECISION";
        public static final String GET_LIST_REGISTER_DECISION_DENOUNCEMENT = "GET_LIST_REGISTER_DECISION_DENOUNCEMENT";
        public static final String GET_LIST_REVIEW_CASE_ACCUSED = "GET_LIST_REVIEW_CASE_ACCUSED";
        public static final String FUN_SEARCH = "fun_search";
        public static final String FUN_GET_CODE_IN_YEAR = "fun_get_code_in_year";
        public static final String GET_LIST_DENOUNCE_DENOUNCEMENT = "GET_LIST_DENOUNCE_DENOUNCEMENT";
        public static final String SEARCH_DENOUNCE_DENOUNCEMENT = "SEARCH_DENOUNCE_DENOUNCEMENT";
        public static final String GET_LIST_VIOLATION = "GET_LIST_VIOLATION";
        public static final String GET_LIST_COMPENSATION = "GET_LIST_COMPENSATION";
    }

    interface REGISTER_DECISION {
        interface TYPE {
            public static final Integer CASE = 1;
            public static final Integer ACCUSED = 2;
            public static final Integer DENOUNCEMENT = 3;
        }
    }

    interface DENOUNCE_DENOUNCEMENT {
        interface SETTLEMENT_STATUS {
            public static final Integer NOT_BEING_SETTLED = 1;
            public static final Integer BEING_SETTLED = 2;
            public static final Integer SUSPENDED = 3;
            public static final Integer SETTLED = 4;
            public static final Integer OVERDUE = 5;
        }
    }
}
