// $ANTLR 3.5.2 SelectClauseParser.g 2017-11-09 09:02:30

package org.apache.hadoop.hive.ql.parse;

import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.hive.conf.HiveConf;


import org.antlr.runtime.*;
import java.util.Stack;
import java.util.List;
import java.util.ArrayList;
import java.util.Map;
import java.util.HashMap;

import org.antlr.runtime.tree.*;


/**
   Licensed to the Apache Software Foundation (ASF) under one or more 
   contributor license agreements.  See the NOTICE file distributed with 
   this work for additional information regarding copyright ownership.
   The ASF licenses this file to You under the Apache License, Version 2.0
   (the "License"); you may not use this file except in compliance with 
   the License.  You may obtain a copy of the License at

       http://www.apache.org/licenses/LICENSE-2.0

   Unless required by applicable law or agreed to in writing, software
   distributed under the License is distributed on an "AS IS" BASIS,
   WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
   See the License for the specific language governing permissions and
   limitations under the License.
*/
@SuppressWarnings("all")
public class HiveParser_SelectClauseParser extends Parser {
	public static final int EOF=-1;
	public static final int AMPERSAND=4;
	public static final int BITWISEOR=5;
	public static final int BITWISEXOR=6;
	public static final int ByteLengthLiteral=7;
	public static final int COLON=8;
	public static final int COMMA=9;
	public static final int CONCATENATE=10;
	public static final int CharSetLiteral=11;
	public static final int CharSetName=12;
	public static final int DIV=13;
	public static final int DIVIDE=14;
	public static final int DOLLAR=15;
	public static final int DOT=16;
	public static final int Digit=17;
	public static final int EQUAL=18;
	public static final int EQUAL_NS=19;
	public static final int Exponent=20;
	public static final int GREATERTHAN=21;
	public static final int GREATERTHANOREQUALTO=22;
	public static final int HexDigit=23;
	public static final int Identifier=24;
	public static final int IntegralLiteral=25;
	public static final int KW_ABORT=26;
	public static final int KW_ADD=27;
	public static final int KW_ADMIN=28;
	public static final int KW_AFTER=29;
	public static final int KW_ALL=30;
	public static final int KW_ALTER=31;
	public static final int KW_ANALYZE=32;
	public static final int KW_AND=33;
	public static final int KW_ARCHIVE=34;
	public static final int KW_ARRAY=35;
	public static final int KW_AS=36;
	public static final int KW_ASC=37;
	public static final int KW_AUTHORIZATION=38;
	public static final int KW_AUTOCOMMIT=39;
	public static final int KW_BEFORE=40;
	public static final int KW_BETWEEN=41;
	public static final int KW_BIGINT=42;
	public static final int KW_BINARY=43;
	public static final int KW_BOOLEAN=44;
	public static final int KW_BOTH=45;
	public static final int KW_BUCKET=46;
	public static final int KW_BUCKETS=47;
	public static final int KW_BY=48;
	public static final int KW_CACHE=49;
	public static final int KW_CASCADE=50;
	public static final int KW_CASE=51;
	public static final int KW_CAST=52;
	public static final int KW_CHANGE=53;
	public static final int KW_CHAR=54;
	public static final int KW_CLUSTER=55;
	public static final int KW_CLUSTERED=56;
	public static final int KW_CLUSTERSTATUS=57;
	public static final int KW_COLLECTION=58;
	public static final int KW_COLUMN=59;
	public static final int KW_COLUMNS=60;
	public static final int KW_COMMENT=61;
	public static final int KW_COMMIT=62;
	public static final int KW_COMPACT=63;
	public static final int KW_COMPACTIONS=64;
	public static final int KW_COMPUTE=65;
	public static final int KW_CONCATENATE=66;
	public static final int KW_CONF=67;
	public static final int KW_CONSTRAINT=68;
	public static final int KW_CONTINUE=69;
	public static final int KW_CREATE=70;
	public static final int KW_CROSS=71;
	public static final int KW_CUBE=72;
	public static final int KW_CURRENT=73;
	public static final int KW_CURRENT_DATE=74;
	public static final int KW_CURRENT_TIMESTAMP=75;
	public static final int KW_CURSOR=76;
	public static final int KW_DATA=77;
	public static final int KW_DATABASE=78;
	public static final int KW_DATABASES=79;
	public static final int KW_DATE=80;
	public static final int KW_DATETIME=81;
	public static final int KW_DAY=82;
	public static final int KW_DBPROPERTIES=83;
	public static final int KW_DECIMAL=84;
	public static final int KW_DEFERRED=85;
	public static final int KW_DEFINED=86;
	public static final int KW_DELETE=87;
	public static final int KW_DELIMITED=88;
	public static final int KW_DEPENDENCY=89;
	public static final int KW_DESC=90;
	public static final int KW_DESCRIBE=91;
	public static final int KW_DETAIL=92;
	public static final int KW_DIRECTORIES=93;
	public static final int KW_DIRECTORY=94;
	public static final int KW_DISABLE=95;
	public static final int KW_DISTINCT=96;
	public static final int KW_DISTRIBUTE=97;
	public static final int KW_DOUBLE=98;
	public static final int KW_DOW=99;
	public static final int KW_DROP=100;
	public static final int KW_DUMP=101;
	public static final int KW_ELEM_TYPE=102;
	public static final int KW_ELSE=103;
	public static final int KW_ENABLE=104;
	public static final int KW_END=105;
	public static final int KW_ESCAPED=106;
	public static final int KW_EXCEPT=107;
	public static final int KW_EXCHANGE=108;
	public static final int KW_EXCLUSIVE=109;
	public static final int KW_EXISTS=110;
	public static final int KW_EXPLAIN=111;
	public static final int KW_EXPORT=112;
	public static final int KW_EXPRESSION=113;
	public static final int KW_EXTENDED=114;
	public static final int KW_EXTERNAL=115;
	public static final int KW_EXTRACT=116;
	public static final int KW_FALSE=117;
	public static final int KW_FETCH=118;
	public static final int KW_FIELDS=119;
	public static final int KW_FILE=120;
	public static final int KW_FILEFORMAT=121;
	public static final int KW_FIRST=122;
	public static final int KW_FLOAT=123;
	public static final int KW_FLOOR=124;
	public static final int KW_FOLLOWING=125;
	public static final int KW_FOR=126;
	public static final int KW_FOREIGN=127;
	public static final int KW_FORMAT=128;
	public static final int KW_FORMATTED=129;
	public static final int KW_FROM=130;
	public static final int KW_FULL=131;
	public static final int KW_FUNCTION=132;
	public static final int KW_FUNCTIONS=133;
	public static final int KW_GRANT=134;
	public static final int KW_GROUP=135;
	public static final int KW_GROUPING=136;
	public static final int KW_HAVING=137;
	public static final int KW_HOUR=138;
	public static final int KW_IDXPROPERTIES=139;
	public static final int KW_IF=140;
	public static final int KW_IMPORT=141;
	public static final int KW_IN=142;
	public static final int KW_INDEX=143;
	public static final int KW_INDEXES=144;
	public static final int KW_INNER=145;
	public static final int KW_INPATH=146;
	public static final int KW_INPUTDRIVER=147;
	public static final int KW_INPUTFORMAT=148;
	public static final int KW_INSERT=149;
	public static final int KW_INT=150;
	public static final int KW_INTERSECT=151;
	public static final int KW_INTERVAL=152;
	public static final int KW_INTO=153;
	public static final int KW_IS=154;
	public static final int KW_ISOLATION=155;
	public static final int KW_ITEMS=156;
	public static final int KW_JAR=157;
	public static final int KW_JOIN=158;
	public static final int KW_KEY=159;
	public static final int KW_KEYS=160;
	public static final int KW_KEY_TYPE=161;
	public static final int KW_LAST=162;
	public static final int KW_LATERAL=163;
	public static final int KW_LEFT=164;
	public static final int KW_LESS=165;
	public static final int KW_LEVEL=166;
	public static final int KW_LIKE=167;
	public static final int KW_LIMIT=168;
	public static final int KW_LINES=169;
	public static final int KW_LOAD=170;
	public static final int KW_LOCAL=171;
	public static final int KW_LOCATION=172;
	public static final int KW_LOCK=173;
	public static final int KW_LOCKS=174;
	public static final int KW_LOGICAL=175;
	public static final int KW_LONG=176;
	public static final int KW_MACRO=177;
	public static final int KW_MAP=178;
	public static final int KW_MAPJOIN=179;
	public static final int KW_MATCHED=180;
	public static final int KW_MATERIALIZED=181;
	public static final int KW_MERGE=182;
	public static final int KW_METADATA=183;
	public static final int KW_MINUS=184;
	public static final int KW_MINUTE=185;
	public static final int KW_MONTH=186;
	public static final int KW_MORE=187;
	public static final int KW_MSCK=188;
	public static final int KW_NONE=189;
	public static final int KW_NORELY=190;
	public static final int KW_NOSCAN=191;
	public static final int KW_NOT=192;
	public static final int KW_NOVALIDATE=193;
	public static final int KW_NULL=194;
	public static final int KW_NULLS=195;
	public static final int KW_OF=196;
	public static final int KW_OFFSET=197;
	public static final int KW_ON=198;
	public static final int KW_ONLY=199;
	public static final int KW_OPERATOR=200;
	public static final int KW_OPTION=201;
	public static final int KW_OR=202;
	public static final int KW_ORDER=203;
	public static final int KW_OUT=204;
	public static final int KW_OUTER=205;
	public static final int KW_OUTPUTDRIVER=206;
	public static final int KW_OUTPUTFORMAT=207;
	public static final int KW_OVER=208;
	public static final int KW_OVERWRITE=209;
	public static final int KW_OWNER=210;
	public static final int KW_PARTIALSCAN=211;
	public static final int KW_PARTITION=212;
	public static final int KW_PARTITIONED=213;
	public static final int KW_PARTITIONS=214;
	public static final int KW_PERCENT=215;
	public static final int KW_PLUS=216;
	public static final int KW_PRECEDING=217;
	public static final int KW_PRECISION=218;
	public static final int KW_PRESERVE=219;
	public static final int KW_PRETTY=220;
	public static final int KW_PRIMARY=221;
	public static final int KW_PRINCIPALS=222;
	public static final int KW_PROCEDURE=223;
	public static final int KW_PURGE=224;
	public static final int KW_QUARTER=225;
	public static final int KW_RANGE=226;
	public static final int KW_READ=227;
	public static final int KW_READS=228;
	public static final int KW_REBUILD=229;
	public static final int KW_RECORDREADER=230;
	public static final int KW_RECORDWRITER=231;
	public static final int KW_REDUCE=232;
	public static final int KW_REFERENCES=233;
	public static final int KW_REGEXP=234;
	public static final int KW_RELOAD=235;
	public static final int KW_RELY=236;
	public static final int KW_RENAME=237;
	public static final int KW_REPAIR=238;
	public static final int KW_REPL=239;
	public static final int KW_REPLACE=240;
	public static final int KW_REPLICATION=241;
	public static final int KW_RESTRICT=242;
	public static final int KW_REVOKE=243;
	public static final int KW_REWRITE=244;
	public static final int KW_RIGHT=245;
	public static final int KW_RLIKE=246;
	public static final int KW_ROLE=247;
	public static final int KW_ROLES=248;
	public static final int KW_ROLLBACK=249;
	public static final int KW_ROLLUP=250;
	public static final int KW_ROW=251;
	public static final int KW_ROWS=252;
	public static final int KW_SCHEMA=253;
	public static final int KW_SCHEMAS=254;
	public static final int KW_SECOND=255;
	public static final int KW_SELECT=256;
	public static final int KW_SEMI=257;
	public static final int KW_SERDE=258;
	public static final int KW_SERDEPROPERTIES=259;
	public static final int KW_SERVER=260;
	public static final int KW_SET=261;
	public static final int KW_SETS=262;
	public static final int KW_SHARED=263;
	public static final int KW_SHOW=264;
	public static final int KW_SHOW_DATABASE=265;
	public static final int KW_SKEWED=266;
	public static final int KW_SMALLINT=267;
	public static final int KW_SNAPSHOT=268;
	public static final int KW_SORT=269;
	public static final int KW_SORTED=270;
	public static final int KW_SSL=271;
	public static final int KW_START=272;
	public static final int KW_STATISTICS=273;
	public static final int KW_STATUS=274;
	public static final int KW_STORED=275;
	public static final int KW_STREAMTABLE=276;
	public static final int KW_STRING=277;
	public static final int KW_STRUCT=278;
	public static final int KW_SUMMARY=279;
	public static final int KW_TABLE=280;
	public static final int KW_TABLES=281;
	public static final int KW_TABLESAMPLE=282;
	public static final int KW_TBLPROPERTIES=283;
	public static final int KW_TEMPORARY=284;
	public static final int KW_TERMINATED=285;
	public static final int KW_THEN=286;
	public static final int KW_TIMESTAMP=287;
	public static final int KW_TINYINT=288;
	public static final int KW_TO=289;
	public static final int KW_TOUCH=290;
	public static final int KW_TRANSACTION=291;
	public static final int KW_TRANSACTIONS=292;
	public static final int KW_TRANSFORM=293;
	public static final int KW_TRIGGER=294;
	public static final int KW_TRUE=295;
	public static final int KW_TRUNCATE=296;
	public static final int KW_UNARCHIVE=297;
	public static final int KW_UNBOUNDED=298;
	public static final int KW_UNDO=299;
	public static final int KW_UNION=300;
	public static final int KW_UNIONTYPE=301;
	public static final int KW_UNIQUEJOIN=302;
	public static final int KW_UNLOCK=303;
	public static final int KW_UNSET=304;
	public static final int KW_UNSIGNED=305;
	public static final int KW_UPDATE=306;
	public static final int KW_URI=307;
	public static final int KW_USE=308;
	public static final int KW_USER=309;
	public static final int KW_USING=310;
	public static final int KW_UTC=311;
	public static final int KW_UTCTIMESTAMP=312;
	public static final int KW_VALIDATE=313;
	public static final int KW_VALUES=314;
	public static final int KW_VALUE_TYPE=315;
	public static final int KW_VARCHAR=316;
	public static final int KW_VECTORIZATION=317;
	public static final int KW_VIEW=318;
	public static final int KW_VIEWS=319;
	public static final int KW_WAIT=320;
	public static final int KW_WEEK=321;
	public static final int KW_WHEN=322;
	public static final int KW_WHERE=323;
	public static final int KW_WHILE=324;
	public static final int KW_WINDOW=325;
	public static final int KW_WITH=326;
	public static final int KW_WORK=327;
	public static final int KW_WRITE=328;
	public static final int KW_YEAR=329;
	public static final int LCURLY=330;
	public static final int LESSTHAN=331;
	public static final int LESSTHANOREQUALTO=332;
	public static final int LINE_COMMENT=333;
	public static final int LPAREN=334;
	public static final int LSQUARE=335;
	public static final int Letter=336;
	public static final int MINUS=337;
	public static final int MOD=338;
	public static final int NOTEQUAL=339;
	public static final int Number=340;
	public static final int NumberLiteral=341;
	public static final int PLUS=342;
	public static final int QUERY_HINT=343;
	public static final int QUESTION=344;
	public static final int QuotedIdentifier=345;
	public static final int RCURLY=346;
	public static final int RPAREN=347;
	public static final int RSQUARE=348;
	public static final int RegexComponent=349;
	public static final int SEMICOLON=350;
	public static final int STAR=351;
	public static final int StringLiteral=352;
	public static final int TILDE=353;
	public static final int WS=354;
	public static final int KW_BATCH=384;
	public static final int KW_DAYOFWEEK=419;
	public static final int KW_HOLD_DDLTIME=462;
	public static final int KW_IGNORE=466;
	public static final int KW_NO_DROP=509;
	public static final int KW_OFFLINE=513;
	public static final int KW_PROTECTION=536;
	public static final int KW_READONLY=541;
	public static final int TOK_ABORT_TRANSACTIONS=648;
	public static final int TOK_ADMIN_OPTION_FOR=649;
	public static final int TOK_ALIASLIST=650;
	public static final int TOK_ALLCOLREF=651;
	public static final int TOK_ALTERDATABASE_OWNER=652;
	public static final int TOK_ALTERDATABASE_PROPERTIES=653;
	public static final int TOK_ALTERINDEX_PROPERTIES=654;
	public static final int TOK_ALTERINDEX_REBUILD=655;
	public static final int TOK_ALTERTABLE=656;
	public static final int TOK_ALTERTABLE_ADDCOLS=657;
	public static final int TOK_ALTERTABLE_ADDCONSTRAINT=658;
	public static final int TOK_ALTERTABLE_ADDPARTS=659;
	public static final int TOK_ALTERTABLE_ARCHIVE=660;
	public static final int TOK_ALTERTABLE_BUCKETS=661;
	public static final int TOK_ALTERTABLE_CHANGECOL_AFTER_POSITION=662;
	public static final int TOK_ALTERTABLE_CLUSTER_SORT=663;
	public static final int TOK_ALTERTABLE_COMPACT=664;
	public static final int TOK_ALTERTABLE_DROPCONSTRAINT=665;
	public static final int TOK_ALTERTABLE_DROPPARTS=666;
	public static final int TOK_ALTERTABLE_DROPPROPERTIES=667;
	public static final int TOK_ALTERTABLE_EXCHANGEPARTITION=668;
	public static final int TOK_ALTERTABLE_FILEFORMAT=669;
	public static final int TOK_ALTERTABLE_LOCATION=670;
	public static final int TOK_ALTERTABLE_MERGEFILES=671;
	public static final int TOK_ALTERTABLE_PARTCOLTYPE=672;
	public static final int TOK_ALTERTABLE_PROPERTIES=673;
	public static final int TOK_ALTERTABLE_RENAME=674;
	public static final int TOK_ALTERTABLE_RENAMECOL=675;
	public static final int TOK_ALTERTABLE_RENAMEPART=676;
	public static final int TOK_ALTERTABLE_REPLACECOLS=677;
	public static final int TOK_ALTERTABLE_SERDEPROPERTIES=678;
	public static final int TOK_ALTERTABLE_SERIALIZER=679;
	public static final int TOK_ALTERTABLE_SKEWED=680;
	public static final int TOK_ALTERTABLE_SKEWED_LOCATION=681;
	public static final int TOK_ALTERTABLE_TOUCH=682;
	public static final int TOK_ALTERTABLE_UNARCHIVE=683;
	public static final int TOK_ALTERTABLE_UPDATECOLSTATS=684;
	public static final int TOK_ALTERTABLE_UPDATESTATS=685;
	public static final int TOK_ALTERVIEW=686;
	public static final int TOK_ALTERVIEW_ADDPARTS=687;
	public static final int TOK_ALTERVIEW_DROPPARTS=688;
	public static final int TOK_ALTERVIEW_DROPPROPERTIES=689;
	public static final int TOK_ALTERVIEW_PROPERTIES=690;
	public static final int TOK_ALTERVIEW_RENAME=691;
	public static final int TOK_ANALYZE=692;
	public static final int TOK_ANONYMOUS=693;
	public static final int TOK_ARCHIVE=694;
	public static final int TOK_BIGINT=695;
	public static final int TOK_BINARY=696;
	public static final int TOK_BLOCKING=697;
	public static final int TOK_BOOLEAN=698;
	public static final int TOK_CACHE_METADATA=699;
	public static final int TOK_CASCADE=700;
	public static final int TOK_CHAR=701;
	public static final int TOK_CHARSETLITERAL=702;
	public static final int TOK_CLUSTERBY=703;
	public static final int TOK_COLTYPELIST=704;
	public static final int TOK_COL_NAME=705;
	public static final int TOK_COMMIT=706;
	public static final int TOK_CREATEDATABASE=707;
	public static final int TOK_CREATEFUNCTION=708;
	public static final int TOK_CREATEINDEX=709;
	public static final int TOK_CREATEINDEX_INDEXTBLNAME=710;
	public static final int TOK_CREATEMACRO=711;
	public static final int TOK_CREATEROLE=712;
	public static final int TOK_CREATETABLE=713;
	public static final int TOK_CREATEVIEW=714;
	public static final int TOK_CREATE_MATERIALIZED_VIEW=715;
	public static final int TOK_CROSSJOIN=716;
	public static final int TOK_CTE=717;
	public static final int TOK_CUBE_GROUPBY=718;
	public static final int TOK_DATABASECOMMENT=719;
	public static final int TOK_DATABASELOCATION=720;
	public static final int TOK_DATABASEPROPERTIES=721;
	public static final int TOK_DATE=722;
	public static final int TOK_DATELITERAL=723;
	public static final int TOK_DATETIME=724;
	public static final int TOK_DBPROPLIST=725;
	public static final int TOK_DB_TYPE=726;
	public static final int TOK_DECIMAL=727;
	public static final int TOK_DEFERRED_REBUILDINDEX=728;
	public static final int TOK_DELETE=729;
	public static final int TOK_DELETE_FROM=730;
	public static final int TOK_DESCDATABASE=731;
	public static final int TOK_DESCFUNCTION=732;
	public static final int TOK_DESCTABLE=733;
	public static final int TOK_DESTINATION=734;
	public static final int TOK_DETAIL=735;
	public static final int TOK_DIR=736;
	public static final int TOK_DISABLE=737;
	public static final int TOK_DISTRIBUTEBY=738;
	public static final int TOK_DOUBLE=739;
	public static final int TOK_DROPDATABASE=740;
	public static final int TOK_DROPFUNCTION=741;
	public static final int TOK_DROPINDEX=742;
	public static final int TOK_DROPMACRO=743;
	public static final int TOK_DROPROLE=744;
	public static final int TOK_DROPTABLE=745;
	public static final int TOK_DROPVIEW=746;
	public static final int TOK_DROP_MATERIALIZED_VIEW=747;
	public static final int TOK_ENABLE=748;
	public static final int TOK_EXCEPTALL=749;
	public static final int TOK_EXCEPTDISTINCT=750;
	public static final int TOK_EXPLAIN=751;
	public static final int TOK_EXPLAIN_SQ_REWRITE=752;
	public static final int TOK_EXPLIST=753;
	public static final int TOK_EXPORT=754;
	public static final int TOK_EXPRESSION=755;
	public static final int TOK_FALSE=756;
	public static final int TOK_FILE=757;
	public static final int TOK_FILEFORMAT_GENERIC=758;
	public static final int TOK_FLOAT=759;
	public static final int TOK_FOREIGN_KEY=760;
	public static final int TOK_FROM=761;
	public static final int TOK_FULLOUTERJOIN=762;
	public static final int TOK_FUNCTION=763;
	public static final int TOK_FUNCTIONDI=764;
	public static final int TOK_FUNCTIONSTAR=765;
	public static final int TOK_GRANT=766;
	public static final int TOK_GRANT_OPTION_FOR=767;
	public static final int TOK_GRANT_ROLE=768;
	public static final int TOK_GRANT_WITH_ADMIN_OPTION=769;
	public static final int TOK_GRANT_WITH_OPTION=770;
	public static final int TOK_GROUP=771;
	public static final int TOK_GROUPBY=772;
	public static final int TOK_GROUPING_SETS=773;
	public static final int TOK_GROUPING_SETS_EXPRESSION=774;
	public static final int TOK_HAVING=775;
	public static final int TOK_IFEXISTS=776;
	public static final int TOK_IFNOTEXISTS=777;
	public static final int TOK_IMPORT=778;
	public static final int TOK_INDEXCOMMENT=779;
	public static final int TOK_INDEXPROPERTIES=780;
	public static final int TOK_INDEXPROPLIST=781;
	public static final int TOK_INSERT=782;
	public static final int TOK_INSERT_INTO=783;
	public static final int TOK_INT=784;
	public static final int TOK_INTERSECTALL=785;
	public static final int TOK_INTERSECTDISTINCT=786;
	public static final int TOK_INTERVAL_DAY_LITERAL=787;
	public static final int TOK_INTERVAL_DAY_TIME=788;
	public static final int TOK_INTERVAL_DAY_TIME_LITERAL=789;
	public static final int TOK_INTERVAL_HOUR_LITERAL=790;
	public static final int TOK_INTERVAL_MINUTE_LITERAL=791;
	public static final int TOK_INTERVAL_MONTH_LITERAL=792;
	public static final int TOK_INTERVAL_SECOND_LITERAL=793;
	public static final int TOK_INTERVAL_YEAR_LITERAL=794;
	public static final int TOK_INTERVAL_YEAR_MONTH=795;
	public static final int TOK_INTERVAL_YEAR_MONTH_LITERAL=796;
	public static final int TOK_ISNOTNULL=797;
	public static final int TOK_ISNULL=798;
	public static final int TOK_ISOLATION_LEVEL=799;
	public static final int TOK_ISOLATION_SNAPSHOT=800;
	public static final int TOK_JAR=801;
	public static final int TOK_JOIN=802;
	public static final int TOK_LATERAL_VIEW=803;
	public static final int TOK_LATERAL_VIEW_OUTER=804;
	public static final int TOK_LEFTOUTERJOIN=805;
	public static final int TOK_LEFTSEMIJOIN=806;
	public static final int TOK_LENGTH=807;
	public static final int TOK_LIKETABLE=808;
	public static final int TOK_LIMIT=809;
	public static final int TOK_LIST=810;
	public static final int TOK_LOAD=811;
	public static final int TOK_LOCKDB=812;
	public static final int TOK_LOCKTABLE=813;
	public static final int TOK_MAP=814;
	public static final int TOK_MATCHED=815;
	public static final int TOK_MERGE=816;
	public static final int TOK_METADATA=817;
	public static final int TOK_MSCK=818;
	public static final int TOK_NORELY=819;
	public static final int TOK_NOT_CLUSTERED=820;
	public static final int TOK_NOT_MATCHED=821;
	public static final int TOK_NOT_SORTED=822;
	public static final int TOK_NOVALIDATE=823;
	public static final int TOK_NO_DROP=824;
	public static final int TOK_NULL=825;
	public static final int TOK_NULLS_FIRST=826;
	public static final int TOK_NULLS_LAST=827;
	public static final int TOK_OFFLINE=828;
	public static final int TOK_OFFSET=829;
	public static final int TOK_ONLY=830;
	public static final int TOK_OPERATOR=831;
	public static final int TOK_OP_ADD=832;
	public static final int TOK_OP_AND=833;
	public static final int TOK_OP_BITAND=834;
	public static final int TOK_OP_BITNOT=835;
	public static final int TOK_OP_BITOR=836;
	public static final int TOK_OP_BITXOR=837;
	public static final int TOK_OP_DIV=838;
	public static final int TOK_OP_EQ=839;
	public static final int TOK_OP_GE=840;
	public static final int TOK_OP_GT=841;
	public static final int TOK_OP_LE=842;
	public static final int TOK_OP_LIKE=843;
	public static final int TOK_OP_LT=844;
	public static final int TOK_OP_MOD=845;
	public static final int TOK_OP_MUL=846;
	public static final int TOK_OP_NE=847;
	public static final int TOK_OP_NOT=848;
	public static final int TOK_OP_OR=849;
	public static final int TOK_OP_SUB=850;
	public static final int TOK_ORDERBY=851;
	public static final int TOK_ORREPLACE=852;
	public static final int TOK_PARTITIONINGSPEC=853;
	public static final int TOK_PARTITIONLOCATION=854;
	public static final int TOK_PARTSPEC=855;
	public static final int TOK_PARTVAL=856;
	public static final int TOK_PERCENT=857;
	public static final int TOK_PRIMARY_KEY=858;
	public static final int TOK_PRINCIPAL_NAME=859;
	public static final int TOK_PRIVILEGE=860;
	public static final int TOK_PRIVILEGE_LIST=861;
	public static final int TOK_PRIV_ALL=862;
	public static final int TOK_PRIV_ALTER_DATA=863;
	public static final int TOK_PRIV_ALTER_METADATA=864;
	public static final int TOK_PRIV_CREATE=865;
	public static final int TOK_PRIV_DELETE=866;
	public static final int TOK_PRIV_DROP=867;
	public static final int TOK_PRIV_INDEX=868;
	public static final int TOK_PRIV_INSERT=869;
	public static final int TOK_PRIV_LOCK=870;
	public static final int TOK_PRIV_OBJECT=871;
	public static final int TOK_PRIV_OBJECT_COL=872;
	public static final int TOK_PRIV_SELECT=873;
	public static final int TOK_PRIV_SHOW_DATABASE=874;
	public static final int TOK_PTBLFUNCTION=875;
	public static final int TOK_QUERY=876;
	public static final int TOK_READONLY=877;
	public static final int TOK_RECORDREADER=878;
	public static final int TOK_RECORDWRITER=879;
	public static final int TOK_RELOADFUNCTION=880;
	public static final int TOK_RELY=881;
	public static final int TOK_REPLICATION=882;
	public static final int TOK_REPL_DUMP=883;
	public static final int TOK_REPL_LOAD=884;
	public static final int TOK_REPL_STATUS=885;
	public static final int TOK_RESOURCE_ALL=886;
	public static final int TOK_RESOURCE_LIST=887;
	public static final int TOK_RESOURCE_URI=888;
	public static final int TOK_RESTRICT=889;
	public static final int TOK_REVOKE=890;
	public static final int TOK_REVOKE_ROLE=891;
	public static final int TOK_REWRITE_DISABLED=892;
	public static final int TOK_REWRITE_ENABLED=893;
	public static final int TOK_RIGHTOUTERJOIN=894;
	public static final int TOK_ROLE=895;
	public static final int TOK_ROLLBACK=896;
	public static final int TOK_ROLLUP_GROUPBY=897;
	public static final int TOK_ROWCOUNT=898;
	public static final int TOK_SELECT=899;
	public static final int TOK_SELECTDI=900;
	public static final int TOK_SELEXPR=901;
	public static final int TOK_SERDE=902;
	public static final int TOK_SERDENAME=903;
	public static final int TOK_SERDEPROPS=904;
	public static final int TOK_SERVER_TYPE=905;
	public static final int TOK_SETCOLREF=906;
	public static final int TOK_SET_AUTOCOMMIT=907;
	public static final int TOK_SET_COLUMNS_CLAUSE=908;
	public static final int TOK_SHOWCOLUMNS=909;
	public static final int TOK_SHOWCONF=910;
	public static final int TOK_SHOWDATABASES=911;
	public static final int TOK_SHOWDBLOCKS=912;
	public static final int TOK_SHOWFUNCTIONS=913;
	public static final int TOK_SHOWINDEXES=914;
	public static final int TOK_SHOWLOCKS=915;
	public static final int TOK_SHOWPARTITIONS=916;
	public static final int TOK_SHOWTABLES=917;
	public static final int TOK_SHOWVIEWS=918;
	public static final int TOK_SHOW_COMPACTIONS=919;
	public static final int TOK_SHOW_CREATEDATABASE=920;
	public static final int TOK_SHOW_CREATETABLE=921;
	public static final int TOK_SHOW_GRANT=922;
	public static final int TOK_SHOW_ROLES=923;
	public static final int TOK_SHOW_ROLE_GRANT=924;
	public static final int TOK_SHOW_ROLE_PRINCIPALS=925;
	public static final int TOK_SHOW_SET_ROLE=926;
	public static final int TOK_SHOW_TABLESTATUS=927;
	public static final int TOK_SHOW_TBLPROPERTIES=928;
	public static final int TOK_SHOW_TRANSACTIONS=929;
	public static final int TOK_SKEWED_LOCATIONS=930;
	public static final int TOK_SKEWED_LOCATION_LIST=931;
	public static final int TOK_SKEWED_LOCATION_MAP=932;
	public static final int TOK_SMALLINT=933;
	public static final int TOK_SORTBY=934;
	public static final int TOK_START_TRANSACTION=935;
	public static final int TOK_STORAGEHANDLER=936;
	public static final int TOK_STOREDASDIRS=937;
	public static final int TOK_STRING=938;
	public static final int TOK_STRINGLITERALSEQUENCE=939;
	public static final int TOK_STRUCT=940;
	public static final int TOK_SUBQUERY=941;
	public static final int TOK_SUBQUERY_EXPR=942;
	public static final int TOK_SUBQUERY_OP=943;
	public static final int TOK_SUBQUERY_OP_NOTEXISTS=944;
	public static final int TOK_SUBQUERY_OP_NOTIN=945;
	public static final int TOK_SUMMARY=946;
	public static final int TOK_SWITCHDATABASE=947;
	public static final int TOK_TAB=948;
	public static final int TOK_TABALIAS=949;
	public static final int TOK_TABCOL=950;
	public static final int TOK_TABCOLLIST=951;
	public static final int TOK_TABCOLNAME=952;
	public static final int TOK_TABCOLVALUE=953;
	public static final int TOK_TABCOLVALUES=954;
	public static final int TOK_TABCOLVALUE_PAIR=955;
	public static final int TOK_TABLEBUCKETSAMPLE=956;
	public static final int TOK_TABLECOMMENT=957;
	public static final int TOK_TABLEFILEFORMAT=958;
	public static final int TOK_TABLELOCATION=959;
	public static final int TOK_TABLEPARTCOLS=960;
	public static final int TOK_TABLEPROPERTIES=961;
	public static final int TOK_TABLEPROPERTY=962;
	public static final int TOK_TABLEPROPLIST=963;
	public static final int TOK_TABLEROWFORMAT=964;
	public static final int TOK_TABLEROWFORMATCOLLITEMS=965;
	public static final int TOK_TABLEROWFORMATFIELD=966;
	public static final int TOK_TABLEROWFORMATLINES=967;
	public static final int TOK_TABLEROWFORMATMAPKEYS=968;
	public static final int TOK_TABLEROWFORMATNULL=969;
	public static final int TOK_TABLESERIALIZER=970;
	public static final int TOK_TABLESKEWED=971;
	public static final int TOK_TABLESPLITSAMPLE=972;
	public static final int TOK_TABLE_OR_COL=973;
	public static final int TOK_TABLE_PARTITION=974;
	public static final int TOK_TABLE_TYPE=975;
	public static final int TOK_TABNAME=976;
	public static final int TOK_TABREF=977;
	public static final int TOK_TABSORTCOLNAMEASC=978;
	public static final int TOK_TABSORTCOLNAMEDESC=979;
	public static final int TOK_TABSRC=980;
	public static final int TOK_TABTYPE=981;
	public static final int TOK_TEMPORARY=982;
	public static final int TOK_TIMESTAMP=983;
	public static final int TOK_TIMESTAMPLITERAL=984;
	public static final int TOK_TINYINT=985;
	public static final int TOK_TMP_FILE=986;
	public static final int TOK_TO=987;
	public static final int TOK_TRANSFORM=988;
	public static final int TOK_TRUE=989;
	public static final int TOK_TRUNCATETABLE=990;
	public static final int TOK_TXN_ACCESS_MODE=991;
	public static final int TOK_TXN_READ_ONLY=992;
	public static final int TOK_TXN_READ_WRITE=993;
	public static final int TOK_UNIONALL=994;
	public static final int TOK_UNIONDISTINCT=995;
	public static final int TOK_UNIONTYPE=996;
	public static final int TOK_UNIQUEJOIN=997;
	public static final int TOK_UNLOCKDB=998;
	public static final int TOK_UNLOCKTABLE=999;
	public static final int TOK_UPDATE=1000;
	public static final int TOK_UPDATE_TABLE=1001;
	public static final int TOK_URI_TYPE=1002;
	public static final int TOK_USER=1003;
	public static final int TOK_USERSCRIPTCOLNAMES=1004;
	public static final int TOK_USERSCRIPTCOLSCHEMA=1005;
	public static final int TOK_VALIDATE=1006;
	public static final int TOK_VALUES_TABLE=1007;
	public static final int TOK_VALUE_ROW=1008;
	public static final int TOK_VARCHAR=1009;
	public static final int TOK_VIEWPARTCOLS=1010;
	public static final int TOK_VIRTUAL_TABLE=1011;
	public static final int TOK_VIRTUAL_TABREF=1012;
	public static final int TOK_WHERE=1013;
	public static final int TOK_WINDOWDEF=1014;
	public static final int TOK_WINDOWRANGE=1015;
	public static final int TOK_WINDOWSPEC=1016;
	public static final int TOK_WINDOWVALUES=1017;

	// delegates
	public Parser[] getDelegates() {
		return new Parser[] {};
	}

	// delegators
	public HiveParser gHiveParser;
	public HiveParser gParent;


	public HiveParser_SelectClauseParser(TokenStream input, HiveParser gHiveParser) {
		this(input, new RecognizerSharedState(), gHiveParser);
	}
	public HiveParser_SelectClauseParser(TokenStream input, RecognizerSharedState state, HiveParser gHiveParser) {
		super(input, state);
		this.gHiveParser = gHiveParser;
		gParent = gHiveParser;
	}

	protected TreeAdaptor adaptor = new CommonTreeAdaptor();

	public void setTreeAdaptor(TreeAdaptor adaptor) {
		this.adaptor = adaptor;
	}
	public TreeAdaptor getTreeAdaptor() {
		return adaptor;
	}
	@Override public String[] getTokenNames() { return HiveParser.tokenNames; }
	@Override public String getGrammarFileName() { return "SelectClauseParser.g"; }


	  @Override
	  public Object recoverFromMismatchedSet(IntStream input,
	      RecognitionException re, BitSet follow) throws RecognitionException {
	    throw re;
	  }
	  @Override
	  public void displayRecognitionError(String[] tokenNames,
	      RecognitionException e) {
	    gParent.errors.add(new ParseError(gParent, e, tokenNames));
	  }


	public static class selectClause_return extends ParserRuleReturnScope {
		ASTNode tree;
		@Override
		public ASTNode getTree() { return tree; }
	};


	// $ANTLR start "selectClause"
	// SelectClauseParser.g:48:1: selectClause : ( KW_SELECT ( QUERY_HINT )? ( ( ( KW_ALL |dist= KW_DISTINCT )? selectList ) | (transform= KW_TRANSFORM selectTrfmClause ) ) -> {$transform == null && $dist == null}? ^( TOK_SELECT ( QUERY_HINT )? selectList ) -> {$transform == null && $dist != null}? ^( TOK_SELECTDI ( QUERY_HINT )? selectList ) -> ^( TOK_SELECT ( QUERY_HINT )? ^( TOK_SELEXPR selectTrfmClause ) ) | trfmClause -> ^( TOK_SELECT ^( TOK_SELEXPR trfmClause ) ) );
	public final HiveParser_SelectClauseParser.selectClause_return selectClause() throws RecognitionException {
		HiveParser_SelectClauseParser.selectClause_return retval = new HiveParser_SelectClauseParser.selectClause_return();
		retval.start = input.LT(1);

		ASTNode root_0 = null;

		Token dist=null;
		Token transform=null;
		Token KW_SELECT1=null;
		Token QUERY_HINT2=null;
		Token KW_ALL3=null;
		ParserRuleReturnScope selectList4 =null;
		ParserRuleReturnScope selectTrfmClause5 =null;
		ParserRuleReturnScope trfmClause6 =null;

		ASTNode dist_tree=null;
		ASTNode transform_tree=null;
		ASTNode KW_SELECT1_tree=null;
		ASTNode QUERY_HINT2_tree=null;
		ASTNode KW_ALL3_tree=null;
		RewriteRuleTokenStream stream_KW_TRANSFORM=new RewriteRuleTokenStream(adaptor,"token KW_TRANSFORM");
		RewriteRuleTokenStream stream_KW_DISTINCT=new RewriteRuleTokenStream(adaptor,"token KW_DISTINCT");
		RewriteRuleTokenStream stream_KW_SELECT=new RewriteRuleTokenStream(adaptor,"token KW_SELECT");
		RewriteRuleTokenStream stream_QUERY_HINT=new RewriteRuleTokenStream(adaptor,"token QUERY_HINT");
		RewriteRuleTokenStream stream_KW_ALL=new RewriteRuleTokenStream(adaptor,"token KW_ALL");
		RewriteRuleSubtreeStream stream_trfmClause=new RewriteRuleSubtreeStream(adaptor,"rule trfmClause");
		RewriteRuleSubtreeStream stream_selectList=new RewriteRuleSubtreeStream(adaptor,"rule selectList");
		RewriteRuleSubtreeStream stream_selectTrfmClause=new RewriteRuleSubtreeStream(adaptor,"rule selectTrfmClause");

		 gParent.pushMsg("select clause", state); 
		try {
			// SelectClauseParser.g:51:5: ( KW_SELECT ( QUERY_HINT )? ( ( ( KW_ALL |dist= KW_DISTINCT )? selectList ) | (transform= KW_TRANSFORM selectTrfmClause ) ) -> {$transform == null && $dist == null}? ^( TOK_SELECT ( QUERY_HINT )? selectList ) -> {$transform == null && $dist != null}? ^( TOK_SELECTDI ( QUERY_HINT )? selectList ) -> ^( TOK_SELECT ( QUERY_HINT )? ^( TOK_SELEXPR selectTrfmClause ) ) | trfmClause -> ^( TOK_SELECT ^( TOK_SELEXPR trfmClause ) ) )
			int alt4=2;
			int LA4_0 = input.LA(1);
			if ( (LA4_0==KW_SELECT) ) {
				alt4=1;
			}
			else if ( (LA4_0==KW_MAP||LA4_0==KW_REDUCE) ) {
				alt4=2;
			}

			else {
				if (state.backtracking>0) {state.failed=true; return retval;}
				NoViableAltException nvae =
					new NoViableAltException("", 4, 0, input);
				throw nvae;
			}

			switch (alt4) {
				case 1 :
					// SelectClauseParser.g:52:5: KW_SELECT ( QUERY_HINT )? ( ( ( KW_ALL |dist= KW_DISTINCT )? selectList ) | (transform= KW_TRANSFORM selectTrfmClause ) )
					{
					KW_SELECT1=(Token)match(input,KW_SELECT,FOLLOW_KW_SELECT_in_selectClause71); if (state.failed) return retval; 
					if ( state.backtracking==0 ) stream_KW_SELECT.add(KW_SELECT1);

					// SelectClauseParser.g:52:15: ( QUERY_HINT )?
					int alt1=2;
					int LA1_0 = input.LA(1);
					if ( (LA1_0==QUERY_HINT) ) {
						alt1=1;
					}
					switch (alt1) {
						case 1 :
							// SelectClauseParser.g:52:15: QUERY_HINT
							{
							QUERY_HINT2=(Token)match(input,QUERY_HINT,FOLLOW_QUERY_HINT_in_selectClause73); if (state.failed) return retval; 
							if ( state.backtracking==0 ) stream_QUERY_HINT.add(QUERY_HINT2);

							}
							break;

					}

					// SelectClauseParser.g:52:27: ( ( ( KW_ALL |dist= KW_DISTINCT )? selectList ) | (transform= KW_TRANSFORM selectTrfmClause ) )
					int alt3=2;
					int LA3_0 = input.LA(1);
					if ( (LA3_0==CharSetName||(LA3_0 >= Identifier && LA3_0 <= KW_ALL)||LA3_0==KW_ANALYZE||(LA3_0 >= KW_ARCHIVE && LA3_0 <= KW_ARRAY)||LA3_0==KW_ASC||(LA3_0 >= KW_AUTOCOMMIT && LA3_0 <= KW_BEFORE)||(LA3_0 >= KW_BIGINT && LA3_0 <= KW_BOOLEAN)||(LA3_0 >= KW_BUCKET && LA3_0 <= KW_BUCKETS)||(LA3_0 >= KW_CACHE && LA3_0 <= KW_CHANGE)||(LA3_0 >= KW_CLUSTER && LA3_0 <= KW_COLLECTION)||(LA3_0 >= KW_COLUMNS && LA3_0 <= KW_COMMENT)||(LA3_0 >= KW_COMPACT && LA3_0 <= KW_CONCATENATE)||LA3_0==KW_CONTINUE||(LA3_0 >= KW_CURRENT_DATE && LA3_0 <= KW_CURRENT_TIMESTAMP)||LA3_0==KW_DATA||(LA3_0 >= KW_DATABASES && LA3_0 <= KW_DBPROPERTIES)||(LA3_0 >= KW_DEFERRED && LA3_0 <= KW_DEFINED)||(LA3_0 >= KW_DELIMITED && LA3_0 <= KW_DESC)||(LA3_0 >= KW_DETAIL && LA3_0 <= KW_DOW)||(LA3_0 >= KW_DUMP && LA3_0 <= KW_ELEM_TYPE)||LA3_0==KW_ENABLE||LA3_0==KW_ESCAPED||(LA3_0 >= KW_EXCLUSIVE && LA3_0 <= KW_EXPRESSION)||(LA3_0 >= KW_EXTRACT && LA3_0 <= KW_FALSE)||(LA3_0 >= KW_FIELDS && LA3_0 <= KW_FLOOR)||(LA3_0 >= KW_FORMAT && LA3_0 <= KW_FORMATTED)||LA3_0==KW_FUNCTIONS||LA3_0==KW_GROUPING||(LA3_0 >= KW_HOUR && LA3_0 <= KW_IF)||(LA3_0 >= KW_INDEX && LA3_0 <= KW_INDEXES)||(LA3_0 >= KW_INPATH && LA3_0 <= KW_INPUTFORMAT)||LA3_0==KW_INT||LA3_0==KW_INTERVAL||(LA3_0 >= KW_ISOLATION && LA3_0 <= KW_JAR)||(LA3_0 >= KW_KEY && LA3_0 <= KW_LAST)||LA3_0==KW_LEVEL||(LA3_0 >= KW_LIMIT && LA3_0 <= KW_LOAD)||(LA3_0 >= KW_LOCATION && LA3_0 <= KW_LONG)||(LA3_0 >= KW_MAP && LA3_0 <= KW_MATERIALIZED)||LA3_0==KW_METADATA||(LA3_0 >= KW_MINUTE && LA3_0 <= KW_MONTH)||LA3_0==KW_MSCK||(LA3_0 >= KW_NORELY && LA3_0 <= KW_NULLS)||LA3_0==KW_OFFSET||(LA3_0 >= KW_OPERATOR && LA3_0 <= KW_OPTION)||(LA3_0 >= KW_OUTPUTDRIVER && LA3_0 <= KW_OUTPUTFORMAT)||(LA3_0 >= KW_OVERWRITE && LA3_0 <= KW_OWNER)||(LA3_0 >= KW_PARTITIONED && LA3_0 <= KW_PARTITIONS)||LA3_0==KW_PLUS||LA3_0==KW_PRETTY||LA3_0==KW_PRINCIPALS||(LA3_0 >= KW_PURGE && LA3_0 <= KW_QUARTER)||LA3_0==KW_READ||(LA3_0 >= KW_REBUILD && LA3_0 <= KW_RECORDWRITER)||(LA3_0 >= KW_RELOAD && LA3_0 <= KW_RESTRICT)||LA3_0==KW_REWRITE||(LA3_0 >= KW_ROLE && LA3_0 <= KW_ROLES)||(LA3_0 >= KW_SCHEMA && LA3_0 <= KW_SECOND)||(LA3_0 >= KW_SEMI && LA3_0 <= KW_SERVER)||(LA3_0 >= KW_SETS && LA3_0 <= KW_SSL)||(LA3_0 >= KW_STATISTICS && LA3_0 <= KW_SUMMARY)||LA3_0==KW_TABLES||(LA3_0 >= KW_TBLPROPERTIES && LA3_0 <= KW_TERMINATED)||(LA3_0 >= KW_TIMESTAMP && LA3_0 <= KW_TINYINT)||(LA3_0 >= KW_TOUCH && LA3_0 <= KW_TRANSACTIONS)||LA3_0==KW_TRUE||LA3_0==KW_UNARCHIVE||LA3_0==KW_UNDO||LA3_0==KW_UNIONTYPE||(LA3_0 >= KW_UNLOCK && LA3_0 <= KW_UNSIGNED)||(LA3_0 >= KW_URI && LA3_0 <= KW_USE)||(LA3_0 >= KW_UTC && LA3_0 <= KW_VALIDATE)||LA3_0==KW_VALUE_TYPE||(LA3_0 >= KW_VECTORIZATION && LA3_0 <= KW_WEEK)||LA3_0==KW_WHILE||(LA3_0 >= KW_WORK && LA3_0 <= KW_YEAR)||LA3_0==LPAREN||LA3_0==MINUS||(LA3_0 >= Number && LA3_0 <= PLUS)||(LA3_0 >= STAR && LA3_0 <= TILDE)||LA3_0==KW_BATCH||LA3_0==KW_DAYOFWEEK||LA3_0==KW_HOLD_DDLTIME||LA3_0==KW_IGNORE||LA3_0==KW_NO_DROP||LA3_0==KW_OFFLINE||LA3_0==KW_PROTECTION||LA3_0==KW_READONLY) ) {
						alt3=1;
					}
					else if ( (LA3_0==KW_TRANSFORM) ) {
						alt3=2;
					}

					else {
						if (state.backtracking>0) {state.failed=true; return retval;}
						NoViableAltException nvae =
							new NoViableAltException("", 3, 0, input);
						throw nvae;
					}

					switch (alt3) {
						case 1 :
							// SelectClauseParser.g:52:28: ( ( KW_ALL |dist= KW_DISTINCT )? selectList )
							{
							// SelectClauseParser.g:52:28: ( ( KW_ALL |dist= KW_DISTINCT )? selectList )
							// SelectClauseParser.g:52:29: ( KW_ALL |dist= KW_DISTINCT )? selectList
							{
							// SelectClauseParser.g:52:29: ( KW_ALL |dist= KW_DISTINCT )?
							int alt2=3;
							int LA2_0 = input.LA(1);
							if ( (LA2_0==KW_ALL) ) {
								alt2=1;
							}
							else if ( (LA2_0==KW_DISTINCT) ) {
								alt2=2;
							}
							switch (alt2) {
								case 1 :
									// SelectClauseParser.g:52:30: KW_ALL
									{
									KW_ALL3=(Token)match(input,KW_ALL,FOLLOW_KW_ALL_in_selectClause79); if (state.failed) return retval; 
									if ( state.backtracking==0 ) stream_KW_ALL.add(KW_ALL3);

									}
									break;
								case 2 :
									// SelectClauseParser.g:52:39: dist= KW_DISTINCT
									{
									dist=(Token)match(input,KW_DISTINCT,FOLLOW_KW_DISTINCT_in_selectClause85); if (state.failed) return retval; 
									if ( state.backtracking==0 ) stream_KW_DISTINCT.add(dist);

									}
									break;

							}

							pushFollow(FOLLOW_selectList_in_selectClause89);
							selectList4=selectList();
							state._fsp--;
							if (state.failed) return retval;
							if ( state.backtracking==0 ) stream_selectList.add(selectList4.getTree());
							}

							}
							break;
						case 2 :
							// SelectClauseParser.g:53:29: (transform= KW_TRANSFORM selectTrfmClause )
							{
							// SelectClauseParser.g:53:29: (transform= KW_TRANSFORM selectTrfmClause )
							// SelectClauseParser.g:53:30: transform= KW_TRANSFORM selectTrfmClause
							{
							transform=(Token)match(input,KW_TRANSFORM,FOLLOW_KW_TRANSFORM_in_selectClause123); if (state.failed) return retval; 
							if ( state.backtracking==0 ) stream_KW_TRANSFORM.add(transform);

							pushFollow(FOLLOW_selectTrfmClause_in_selectClause125);
							selectTrfmClause5=selectTrfmClause();
							state._fsp--;
							if (state.failed) return retval;
							if ( state.backtracking==0 ) stream_selectTrfmClause.add(selectTrfmClause5.getTree());
							}

							}
							break;

					}

					// AST REWRITE
					// elements: QUERY_HINT, selectList, selectTrfmClause, QUERY_HINT, selectList, QUERY_HINT
					// token labels: 
					// rule labels: retval
					// token list labels: 
					// rule list labels: 
					// wildcard labels: 
					if ( state.backtracking==0 ) {
					retval.tree = root_0;
					RewriteRuleSubtreeStream stream_retval=new RewriteRuleSubtreeStream(adaptor,"rule retval",retval!=null?retval.getTree():null);

					root_0 = (ASTNode)adaptor.nil();
					// 54:6: -> {$transform == null && $dist == null}? ^( TOK_SELECT ( QUERY_HINT )? selectList )
					if (transform == null && dist == null) {
						// SelectClauseParser.g:54:48: ^( TOK_SELECT ( QUERY_HINT )? selectList )
						{
						ASTNode root_1 = (ASTNode)adaptor.nil();
						root_1 = (ASTNode)adaptor.becomeRoot((ASTNode)adaptor.create(TOK_SELECT, "TOK_SELECT"), root_1);
						// SelectClauseParser.g:54:61: ( QUERY_HINT )?
						if ( stream_QUERY_HINT.hasNext() ) {
							adaptor.addChild(root_1, stream_QUERY_HINT.nextNode());
						}
						stream_QUERY_HINT.reset();

						adaptor.addChild(root_1, stream_selectList.nextTree());
						adaptor.addChild(root_0, root_1);
						}

					}

					else // 55:6: -> {$transform == null && $dist != null}? ^( TOK_SELECTDI ( QUERY_HINT )? selectList )
					if (transform == null && dist != null) {
						// SelectClauseParser.g:55:48: ^( TOK_SELECTDI ( QUERY_HINT )? selectList )
						{
						ASTNode root_1 = (ASTNode)adaptor.nil();
						root_1 = (ASTNode)adaptor.becomeRoot((ASTNode)adaptor.create(TOK_SELECTDI, "TOK_SELECTDI"), root_1);
						// SelectClauseParser.g:55:63: ( QUERY_HINT )?
						if ( stream_QUERY_HINT.hasNext() ) {
							adaptor.addChild(root_1, stream_QUERY_HINT.nextNode());
						}
						stream_QUERY_HINT.reset();

						adaptor.addChild(root_1, stream_selectList.nextTree());
						adaptor.addChild(root_0, root_1);
						}

					}

					else // 56:6: -> ^( TOK_SELECT ( QUERY_HINT )? ^( TOK_SELEXPR selectTrfmClause ) )
					{
						// SelectClauseParser.g:56:9: ^( TOK_SELECT ( QUERY_HINT )? ^( TOK_SELEXPR selectTrfmClause ) )
						{
						ASTNode root_1 = (ASTNode)adaptor.nil();
						root_1 = (ASTNode)adaptor.becomeRoot((ASTNode)adaptor.create(TOK_SELECT, "TOK_SELECT"), root_1);
						// SelectClauseParser.g:56:22: ( QUERY_HINT )?
						if ( stream_QUERY_HINT.hasNext() ) {
							adaptor.addChild(root_1, stream_QUERY_HINT.nextNode());
						}
						stream_QUERY_HINT.reset();

						// SelectClauseParser.g:56:34: ^( TOK_SELEXPR selectTrfmClause )
						{
						ASTNode root_2 = (ASTNode)adaptor.nil();
						root_2 = (ASTNode)adaptor.becomeRoot((ASTNode)adaptor.create(TOK_SELEXPR, "TOK_SELEXPR"), root_2);
						adaptor.addChild(root_2, stream_selectTrfmClause.nextTree());
						adaptor.addChild(root_1, root_2);
						}

						adaptor.addChild(root_0, root_1);
						}

					}


					retval.tree = root_0;
					}

					}
					break;
				case 2 :
					// SelectClauseParser.g:58:5: trfmClause
					{
					pushFollow(FOLLOW_trfmClause_in_selectClause196);
					trfmClause6=trfmClause();
					state._fsp--;
					if (state.failed) return retval;
					if ( state.backtracking==0 ) stream_trfmClause.add(trfmClause6.getTree());
					// AST REWRITE
					// elements: trfmClause
					// token labels: 
					// rule labels: retval
					// token list labels: 
					// rule list labels: 
					// wildcard labels: 
					if ( state.backtracking==0 ) {
					retval.tree = root_0;
					RewriteRuleSubtreeStream stream_retval=new RewriteRuleSubtreeStream(adaptor,"rule retval",retval!=null?retval.getTree():null);

					root_0 = (ASTNode)adaptor.nil();
					// 58:17: -> ^( TOK_SELECT ^( TOK_SELEXPR trfmClause ) )
					{
						// SelectClauseParser.g:58:19: ^( TOK_SELECT ^( TOK_SELEXPR trfmClause ) )
						{
						ASTNode root_1 = (ASTNode)adaptor.nil();
						root_1 = (ASTNode)adaptor.becomeRoot((ASTNode)adaptor.create(TOK_SELECT, "TOK_SELECT"), root_1);
						// SelectClauseParser.g:58:32: ^( TOK_SELEXPR trfmClause )
						{
						ASTNode root_2 = (ASTNode)adaptor.nil();
						root_2 = (ASTNode)adaptor.becomeRoot((ASTNode)adaptor.create(TOK_SELEXPR, "TOK_SELEXPR"), root_2);
						adaptor.addChild(root_2, stream_trfmClause.nextTree());
						adaptor.addChild(root_1, root_2);
						}

						adaptor.addChild(root_0, root_1);
						}

					}


					retval.tree = root_0;
					}

					}
					break;

			}
			retval.stop = input.LT(-1);

			if ( state.backtracking==0 ) {
			retval.tree = (ASTNode)adaptor.rulePostProcessing(root_0);
			adaptor.setTokenBoundaries(retval.tree, retval.start, retval.stop);
			}
			if ( state.backtracking==0 ) { gParent.popMsg(state); }
		}

		catch (RecognitionException e) {
		  throw e;
		}

		finally {
			// do for sure before leaving
		}
		return retval;
	}
	// $ANTLR end "selectClause"


	public static class selectList_return extends ParserRuleReturnScope {
		ASTNode tree;
		@Override
		public ASTNode getTree() { return tree; }
	};


	// $ANTLR start "selectList"
	// SelectClauseParser.g:61:1: selectList : selectItem ( COMMA selectItem )* -> ( selectItem )+ ;
	public final HiveParser_SelectClauseParser.selectList_return selectList() throws RecognitionException {
		HiveParser_SelectClauseParser.selectList_return retval = new HiveParser_SelectClauseParser.selectList_return();
		retval.start = input.LT(1);

		ASTNode root_0 = null;

		Token COMMA8=null;
		ParserRuleReturnScope selectItem7 =null;
		ParserRuleReturnScope selectItem9 =null;

		ASTNode COMMA8_tree=null;
		RewriteRuleTokenStream stream_COMMA=new RewriteRuleTokenStream(adaptor,"token COMMA");
		RewriteRuleSubtreeStream stream_selectItem=new RewriteRuleSubtreeStream(adaptor,"rule selectItem");

		 gParent.pushMsg("select list", state); 
		try {
			// SelectClauseParser.g:64:5: ( selectItem ( COMMA selectItem )* -> ( selectItem )+ )
			// SelectClauseParser.g:65:5: selectItem ( COMMA selectItem )*
			{
			pushFollow(FOLLOW_selectItem_in_selectList239);
			selectItem7=selectItem();
			state._fsp--;
			if (state.failed) return retval;
			if ( state.backtracking==0 ) stream_selectItem.add(selectItem7.getTree());
			// SelectClauseParser.g:65:16: ( COMMA selectItem )*
			loop5:
			while (true) {
				int alt5=2;
				int LA5_0 = input.LA(1);
				if ( (LA5_0==COMMA) ) {
					alt5=1;
				}

				switch (alt5) {
				case 1 :
					// SelectClauseParser.g:65:18: COMMA selectItem
					{
					COMMA8=(Token)match(input,COMMA,FOLLOW_COMMA_in_selectList243); if (state.failed) return retval; 
					if ( state.backtracking==0 ) stream_COMMA.add(COMMA8);

					pushFollow(FOLLOW_selectItem_in_selectList246);
					selectItem9=selectItem();
					state._fsp--;
					if (state.failed) return retval;
					if ( state.backtracking==0 ) stream_selectItem.add(selectItem9.getTree());
					}
					break;

				default :
					break loop5;
				}
			}

			// AST REWRITE
			// elements: selectItem
			// token labels: 
			// rule labels: retval
			// token list labels: 
			// rule list labels: 
			// wildcard labels: 
			if ( state.backtracking==0 ) {
			retval.tree = root_0;
			RewriteRuleSubtreeStream stream_retval=new RewriteRuleSubtreeStream(adaptor,"rule retval",retval!=null?retval.getTree():null);

			root_0 = (ASTNode)adaptor.nil();
			// 65:39: -> ( selectItem )+
			{
				if ( !(stream_selectItem.hasNext()) ) {
					throw new RewriteEarlyExitException();
				}
				while ( stream_selectItem.hasNext() ) {
					adaptor.addChild(root_0, stream_selectItem.nextTree());
				}
				stream_selectItem.reset();

			}


			retval.tree = root_0;
			}

			}

			retval.stop = input.LT(-1);

			if ( state.backtracking==0 ) {
			retval.tree = (ASTNode)adaptor.rulePostProcessing(root_0);
			adaptor.setTokenBoundaries(retval.tree, retval.start, retval.stop);
			}
			if ( state.backtracking==0 ) { gParent.popMsg(state); }
		}

		catch (RecognitionException e) {
		  throw e;
		}

		finally {
			// do for sure before leaving
		}
		return retval;
	}
	// $ANTLR end "selectList"


	public static class selectTrfmClause_return extends ParserRuleReturnScope {
		ASTNode tree;
		@Override
		public ASTNode getTree() { return tree; }
	};


	// $ANTLR start "selectTrfmClause"
	// SelectClauseParser.g:68:1: selectTrfmClause : LPAREN selectExpressionList RPAREN inSerde= rowFormat inRec= recordWriter KW_USING StringLiteral ( KW_AS ( ( LPAREN ( aliasList | columnNameTypeList ) RPAREN ) | ( aliasList | columnNameTypeList ) ) )? outSerde= rowFormat outRec= recordReader -> ^( TOK_TRANSFORM selectExpressionList $inSerde $inRec StringLiteral $outSerde $outRec ( aliasList )? ( columnNameTypeList )? ) ;
	public final HiveParser_SelectClauseParser.selectTrfmClause_return selectTrfmClause() throws RecognitionException {
		HiveParser_SelectClauseParser.selectTrfmClause_return retval = new HiveParser_SelectClauseParser.selectTrfmClause_return();
		retval.start = input.LT(1);

		ASTNode root_0 = null;

		Token LPAREN10=null;
		Token RPAREN12=null;
		Token KW_USING13=null;
		Token StringLiteral14=null;
		Token KW_AS15=null;
		Token LPAREN16=null;
		Token RPAREN19=null;
		ParserRuleReturnScope inSerde =null;
		ParserRuleReturnScope inRec =null;
		ParserRuleReturnScope outSerde =null;
		ParserRuleReturnScope outRec =null;
		ParserRuleReturnScope selectExpressionList11 =null;
		ParserRuleReturnScope aliasList17 =null;
		ParserRuleReturnScope columnNameTypeList18 =null;
		ParserRuleReturnScope aliasList20 =null;
		ParserRuleReturnScope columnNameTypeList21 =null;

		ASTNode LPAREN10_tree=null;
		ASTNode RPAREN12_tree=null;
		ASTNode KW_USING13_tree=null;
		ASTNode StringLiteral14_tree=null;
		ASTNode KW_AS15_tree=null;
		ASTNode LPAREN16_tree=null;
		ASTNode RPAREN19_tree=null;
		RewriteRuleTokenStream stream_StringLiteral=new RewriteRuleTokenStream(adaptor,"token StringLiteral");
		RewriteRuleTokenStream stream_KW_USING=new RewriteRuleTokenStream(adaptor,"token KW_USING");
		RewriteRuleTokenStream stream_LPAREN=new RewriteRuleTokenStream(adaptor,"token LPAREN");
		RewriteRuleTokenStream stream_RPAREN=new RewriteRuleTokenStream(adaptor,"token RPAREN");
		RewriteRuleTokenStream stream_KW_AS=new RewriteRuleTokenStream(adaptor,"token KW_AS");
		RewriteRuleSubtreeStream stream_aliasList=new RewriteRuleSubtreeStream(adaptor,"rule aliasList");
		RewriteRuleSubtreeStream stream_rowFormat=new RewriteRuleSubtreeStream(adaptor,"rule rowFormat");
		RewriteRuleSubtreeStream stream_columnNameTypeList=new RewriteRuleSubtreeStream(adaptor,"rule columnNameTypeList");
		RewriteRuleSubtreeStream stream_recordReader=new RewriteRuleSubtreeStream(adaptor,"rule recordReader");
		RewriteRuleSubtreeStream stream_selectExpressionList=new RewriteRuleSubtreeStream(adaptor,"rule selectExpressionList");
		RewriteRuleSubtreeStream stream_recordWriter=new RewriteRuleSubtreeStream(adaptor,"rule recordWriter");

		 gParent.pushMsg("transform clause", state); 
		try {
			// SelectClauseParser.g:71:5: ( LPAREN selectExpressionList RPAREN inSerde= rowFormat inRec= recordWriter KW_USING StringLiteral ( KW_AS ( ( LPAREN ( aliasList | columnNameTypeList ) RPAREN ) | ( aliasList | columnNameTypeList ) ) )? outSerde= rowFormat outRec= recordReader -> ^( TOK_TRANSFORM selectExpressionList $inSerde $inRec StringLiteral $outSerde $outRec ( aliasList )? ( columnNameTypeList )? ) )
			// SelectClauseParser.g:72:5: LPAREN selectExpressionList RPAREN inSerde= rowFormat inRec= recordWriter KW_USING StringLiteral ( KW_AS ( ( LPAREN ( aliasList | columnNameTypeList ) RPAREN ) | ( aliasList | columnNameTypeList ) ) )? outSerde= rowFormat outRec= recordReader
			{
			LPAREN10=(Token)match(input,LPAREN,FOLLOW_LPAREN_in_selectTrfmClause285); if (state.failed) return retval; 
			if ( state.backtracking==0 ) stream_LPAREN.add(LPAREN10);

			pushFollow(FOLLOW_selectExpressionList_in_selectTrfmClause287);
			selectExpressionList11=selectExpressionList();
			state._fsp--;
			if (state.failed) return retval;
			if ( state.backtracking==0 ) stream_selectExpressionList.add(selectExpressionList11.getTree());
			RPAREN12=(Token)match(input,RPAREN,FOLLOW_RPAREN_in_selectTrfmClause289); if (state.failed) return retval; 
			if ( state.backtracking==0 ) stream_RPAREN.add(RPAREN12);

			pushFollow(FOLLOW_rowFormat_in_selectTrfmClause297);
			inSerde=gHiveParser.rowFormat();
			state._fsp--;
			if (state.failed) return retval;
			if ( state.backtracking==0 ) stream_rowFormat.add(inSerde.getTree());
			pushFollow(FOLLOW_recordWriter_in_selectTrfmClause301);
			inRec=gHiveParser.recordWriter();
			state._fsp--;
			if (state.failed) return retval;
			if ( state.backtracking==0 ) stream_recordWriter.add(inRec.getTree());
			KW_USING13=(Token)match(input,KW_USING,FOLLOW_KW_USING_in_selectTrfmClause307); if (state.failed) return retval; 
			if ( state.backtracking==0 ) stream_KW_USING.add(KW_USING13);

			StringLiteral14=(Token)match(input,StringLiteral,FOLLOW_StringLiteral_in_selectTrfmClause309); if (state.failed) return retval; 
			if ( state.backtracking==0 ) stream_StringLiteral.add(StringLiteral14);

			// SelectClauseParser.g:75:5: ( KW_AS ( ( LPAREN ( aliasList | columnNameTypeList ) RPAREN ) | ( aliasList | columnNameTypeList ) ) )?
			int alt9=2;
			int LA9_0 = input.LA(1);
			if ( (LA9_0==KW_AS) ) {
				alt9=1;
			}
			switch (alt9) {
				case 1 :
					// SelectClauseParser.g:75:7: KW_AS ( ( LPAREN ( aliasList | columnNameTypeList ) RPAREN ) | ( aliasList | columnNameTypeList ) )
					{
					KW_AS15=(Token)match(input,KW_AS,FOLLOW_KW_AS_in_selectTrfmClause317); if (state.failed) return retval; 
					if ( state.backtracking==0 ) stream_KW_AS.add(KW_AS15);

					// SelectClauseParser.g:75:13: ( ( LPAREN ( aliasList | columnNameTypeList ) RPAREN ) | ( aliasList | columnNameTypeList ) )
					int alt8=2;
					int LA8_0 = input.LA(1);
					if ( (LA8_0==LPAREN) ) {
						alt8=1;
					}
					else if ( (LA8_0==Identifier||(LA8_0 >= KW_ABORT && LA8_0 <= KW_AFTER)||LA8_0==KW_ANALYZE||LA8_0==KW_ARCHIVE||LA8_0==KW_ASC||(LA8_0 >= KW_AUTOCOMMIT && LA8_0 <= KW_BEFORE)||(LA8_0 >= KW_BUCKET && LA8_0 <= KW_BUCKETS)||(LA8_0 >= KW_CACHE && LA8_0 <= KW_CASCADE)||LA8_0==KW_CHANGE||(LA8_0 >= KW_CLUSTER && LA8_0 <= KW_COLLECTION)||(LA8_0 >= KW_COLUMNS && LA8_0 <= KW_COMMENT)||(LA8_0 >= KW_COMPACT && LA8_0 <= KW_CONCATENATE)||LA8_0==KW_CONTINUE||LA8_0==KW_DATA||LA8_0==KW_DATABASES||(LA8_0 >= KW_DATETIME && LA8_0 <= KW_DBPROPERTIES)||(LA8_0 >= KW_DEFERRED && LA8_0 <= KW_DEFINED)||(LA8_0 >= KW_DELIMITED && LA8_0 <= KW_DESC)||(LA8_0 >= KW_DETAIL && LA8_0 <= KW_DISABLE)||LA8_0==KW_DISTRIBUTE||LA8_0==KW_DOW||(LA8_0 >= KW_DUMP && LA8_0 <= KW_ELEM_TYPE)||LA8_0==KW_ENABLE||LA8_0==KW_ESCAPED||LA8_0==KW_EXCLUSIVE||(LA8_0 >= KW_EXPLAIN && LA8_0 <= KW_EXPRESSION)||(LA8_0 >= KW_FIELDS && LA8_0 <= KW_FIRST)||(LA8_0 >= KW_FORMAT && LA8_0 <= KW_FORMATTED)||LA8_0==KW_FUNCTIONS||(LA8_0 >= KW_HOUR && LA8_0 <= KW_IDXPROPERTIES)||(LA8_0 >= KW_INDEX && LA8_0 <= KW_INDEXES)||(LA8_0 >= KW_INPATH && LA8_0 <= KW_INPUTFORMAT)||(LA8_0 >= KW_ISOLATION && LA8_0 <= KW_JAR)||(LA8_0 >= KW_KEY && LA8_0 <= KW_LAST)||LA8_0==KW_LEVEL||(LA8_0 >= KW_LIMIT && LA8_0 <= KW_LOAD)||(LA8_0 >= KW_LOCATION && LA8_0 <= KW_LONG)||(LA8_0 >= KW_MAPJOIN && LA8_0 <= KW_MATERIALIZED)||LA8_0==KW_METADATA||(LA8_0 >= KW_MINUTE && LA8_0 <= KW_MONTH)||LA8_0==KW_MSCK||(LA8_0 >= KW_NORELY && LA8_0 <= KW_NOSCAN)||LA8_0==KW_NOVALIDATE||LA8_0==KW_NULLS||LA8_0==KW_OFFSET||(LA8_0 >= KW_OPERATOR && LA8_0 <= KW_OPTION)||(LA8_0 >= KW_OUTPUTDRIVER && LA8_0 <= KW_OUTPUTFORMAT)||(LA8_0 >= KW_OVERWRITE && LA8_0 <= KW_OWNER)||(LA8_0 >= KW_PARTITIONED && LA8_0 <= KW_PARTITIONS)||LA8_0==KW_PLUS||LA8_0==KW_PRETTY||LA8_0==KW_PRINCIPALS||(LA8_0 >= KW_PURGE && LA8_0 <= KW_QUARTER)||LA8_0==KW_READ||(LA8_0 >= KW_REBUILD && LA8_0 <= KW_RECORDWRITER)||(LA8_0 >= KW_RELOAD && LA8_0 <= KW_RESTRICT)||LA8_0==KW_REWRITE||(LA8_0 >= KW_ROLE && LA8_0 <= KW_ROLES)||(LA8_0 >= KW_SCHEMA && LA8_0 <= KW_SECOND)||(LA8_0 >= KW_SEMI && LA8_0 <= KW_SERVER)||(LA8_0 >= KW_SETS && LA8_0 <= KW_SKEWED)||(LA8_0 >= KW_SNAPSHOT && LA8_0 <= KW_SSL)||(LA8_0 >= KW_STATISTICS && LA8_0 <= KW_SUMMARY)||LA8_0==KW_TABLES||(LA8_0 >= KW_TBLPROPERTIES && LA8_0 <= KW_TERMINATED)||LA8_0==KW_TINYINT||(LA8_0 >= KW_TOUCH && LA8_0 <= KW_TRANSACTIONS)||LA8_0==KW_UNARCHIVE||LA8_0==KW_UNDO||LA8_0==KW_UNIONTYPE||(LA8_0 >= KW_UNLOCK && LA8_0 <= KW_UNSIGNED)||(LA8_0 >= KW_URI && LA8_0 <= KW_USE)||(LA8_0 >= KW_UTC && LA8_0 <= KW_VALIDATE)||LA8_0==KW_VALUE_TYPE||(LA8_0 >= KW_VECTORIZATION && LA8_0 <= KW_WEEK)||LA8_0==KW_WHILE||(LA8_0 >= KW_WORK && LA8_0 <= KW_YEAR)||LA8_0==KW_BATCH||LA8_0==KW_DAYOFWEEK||LA8_0==KW_HOLD_DDLTIME||LA8_0==KW_IGNORE||LA8_0==KW_NO_DROP||LA8_0==KW_OFFLINE||LA8_0==KW_PROTECTION||LA8_0==KW_READONLY) ) {
						alt8=2;
					}

					else {
						if (state.backtracking>0) {state.failed=true; return retval;}
						NoViableAltException nvae =
							new NoViableAltException("", 8, 0, input);
						throw nvae;
					}

					switch (alt8) {
						case 1 :
							// SelectClauseParser.g:75:14: ( LPAREN ( aliasList | columnNameTypeList ) RPAREN )
							{
							// SelectClauseParser.g:75:14: ( LPAREN ( aliasList | columnNameTypeList ) RPAREN )
							// SelectClauseParser.g:75:15: LPAREN ( aliasList | columnNameTypeList ) RPAREN
							{
							LPAREN16=(Token)match(input,LPAREN,FOLLOW_LPAREN_in_selectTrfmClause321); if (state.failed) return retval; 
							if ( state.backtracking==0 ) stream_LPAREN.add(LPAREN16);

							// SelectClauseParser.g:75:22: ( aliasList | columnNameTypeList )
							int alt6=2;
							int LA6_0 = input.LA(1);
							if ( (LA6_0==Identifier) ) {
								int LA6_1 = input.LA(2);
								if ( (LA6_1==COMMA||LA6_1==RPAREN) ) {
									alt6=1;
								}
								else if ( (LA6_1==KW_ARRAY||(LA6_1 >= KW_BIGINT && LA6_1 <= KW_BOOLEAN)||LA6_1==KW_CHAR||(LA6_1 >= KW_DATE && LA6_1 <= KW_DATETIME)||LA6_1==KW_DECIMAL||LA6_1==KW_DOUBLE||LA6_1==KW_FLOAT||LA6_1==KW_INT||LA6_1==KW_MAP||LA6_1==KW_SMALLINT||(LA6_1 >= KW_STRING && LA6_1 <= KW_STRUCT)||(LA6_1 >= KW_TIMESTAMP && LA6_1 <= KW_TINYINT)||LA6_1==KW_UNIONTYPE||LA6_1==KW_VARCHAR) ) {
									alt6=2;
								}

								else {
									if (state.backtracking>0) {state.failed=true; return retval;}
									int nvaeMark = input.mark();
									try {
										input.consume();
										NoViableAltException nvae =
											new NoViableAltException("", 6, 1, input);
										throw nvae;
									} finally {
										input.rewind(nvaeMark);
									}
								}

							}
							else if ( ((LA6_0 >= KW_ABORT && LA6_0 <= KW_AFTER)||LA6_0==KW_ANALYZE||LA6_0==KW_ARCHIVE||LA6_0==KW_ASC||(LA6_0 >= KW_AUTOCOMMIT && LA6_0 <= KW_BEFORE)||(LA6_0 >= KW_BUCKET && LA6_0 <= KW_BUCKETS)||(LA6_0 >= KW_CACHE && LA6_0 <= KW_CASCADE)||LA6_0==KW_CHANGE||(LA6_0 >= KW_CLUSTER && LA6_0 <= KW_COLLECTION)||(LA6_0 >= KW_COLUMNS && LA6_0 <= KW_COMMENT)||(LA6_0 >= KW_COMPACT && LA6_0 <= KW_CONCATENATE)||LA6_0==KW_CONTINUE||LA6_0==KW_DATA||LA6_0==KW_DATABASES||(LA6_0 >= KW_DATETIME && LA6_0 <= KW_DBPROPERTIES)||(LA6_0 >= KW_DEFERRED && LA6_0 <= KW_DEFINED)||(LA6_0 >= KW_DELIMITED && LA6_0 <= KW_DESC)||(LA6_0 >= KW_DETAIL && LA6_0 <= KW_DISABLE)||LA6_0==KW_DISTRIBUTE||LA6_0==KW_DOW||(LA6_0 >= KW_DUMP && LA6_0 <= KW_ELEM_TYPE)||LA6_0==KW_ENABLE||LA6_0==KW_ESCAPED||LA6_0==KW_EXCLUSIVE||(LA6_0 >= KW_EXPLAIN && LA6_0 <= KW_EXPRESSION)||(LA6_0 >= KW_FIELDS && LA6_0 <= KW_FIRST)||(LA6_0 >= KW_FORMAT && LA6_0 <= KW_FORMATTED)||LA6_0==KW_FUNCTIONS||(LA6_0 >= KW_HOUR && LA6_0 <= KW_IDXPROPERTIES)||(LA6_0 >= KW_INDEX && LA6_0 <= KW_INDEXES)||(LA6_0 >= KW_INPATH && LA6_0 <= KW_INPUTFORMAT)||(LA6_0 >= KW_ISOLATION && LA6_0 <= KW_JAR)||(LA6_0 >= KW_KEY && LA6_0 <= KW_LAST)||LA6_0==KW_LEVEL||(LA6_0 >= KW_LIMIT && LA6_0 <= KW_LOAD)||(LA6_0 >= KW_LOCATION && LA6_0 <= KW_LONG)||(LA6_0 >= KW_MAPJOIN && LA6_0 <= KW_MATERIALIZED)||LA6_0==KW_METADATA||(LA6_0 >= KW_MINUTE && LA6_0 <= KW_MONTH)||LA6_0==KW_MSCK||(LA6_0 >= KW_NORELY && LA6_0 <= KW_NOSCAN)||LA6_0==KW_NOVALIDATE||LA6_0==KW_NULLS||LA6_0==KW_OFFSET||(LA6_0 >= KW_OPERATOR && LA6_0 <= KW_OPTION)||(LA6_0 >= KW_OUTPUTDRIVER && LA6_0 <= KW_OUTPUTFORMAT)||(LA6_0 >= KW_OVERWRITE && LA6_0 <= KW_OWNER)||(LA6_0 >= KW_PARTITIONED && LA6_0 <= KW_PARTITIONS)||LA6_0==KW_PLUS||LA6_0==KW_PRETTY||LA6_0==KW_PRINCIPALS||(LA6_0 >= KW_PURGE && LA6_0 <= KW_QUARTER)||LA6_0==KW_READ||(LA6_0 >= KW_REBUILD && LA6_0 <= KW_RECORDWRITER)||(LA6_0 >= KW_RELOAD && LA6_0 <= KW_RESTRICT)||LA6_0==KW_REWRITE||(LA6_0 >= KW_ROLE && LA6_0 <= KW_ROLES)||(LA6_0 >= KW_SCHEMA && LA6_0 <= KW_SECOND)||(LA6_0 >= KW_SEMI && LA6_0 <= KW_SERVER)||(LA6_0 >= KW_SETS && LA6_0 <= KW_SKEWED)||(LA6_0 >= KW_SNAPSHOT && LA6_0 <= KW_SSL)||(LA6_0 >= KW_STATISTICS && LA6_0 <= KW_SUMMARY)||LA6_0==KW_TABLES||(LA6_0 >= KW_TBLPROPERTIES && LA6_0 <= KW_TERMINATED)||LA6_0==KW_TINYINT||(LA6_0 >= KW_TOUCH && LA6_0 <= KW_TRANSACTIONS)||LA6_0==KW_UNARCHIVE||LA6_0==KW_UNDO||LA6_0==KW_UNIONTYPE||(LA6_0 >= KW_UNLOCK && LA6_0 <= KW_UNSIGNED)||(LA6_0 >= KW_URI && LA6_0 <= KW_USE)||(LA6_0 >= KW_UTC && LA6_0 <= KW_VALIDATE)||LA6_0==KW_VALUE_TYPE||(LA6_0 >= KW_VECTORIZATION && LA6_0 <= KW_WEEK)||LA6_0==KW_WHILE||(LA6_0 >= KW_WORK && LA6_0 <= KW_YEAR)||LA6_0==KW_BATCH||LA6_0==KW_DAYOFWEEK||LA6_0==KW_HOLD_DDLTIME||LA6_0==KW_IGNORE||LA6_0==KW_NO_DROP||LA6_0==KW_OFFLINE||LA6_0==KW_PROTECTION||LA6_0==KW_READONLY) ) {
								int LA6_2 = input.LA(2);
								if ( (LA6_2==COMMA||LA6_2==RPAREN) ) {
									alt6=1;
								}
								else if ( (LA6_2==KW_ARRAY||(LA6_2 >= KW_BIGINT && LA6_2 <= KW_BOOLEAN)||LA6_2==KW_CHAR||(LA6_2 >= KW_DATE && LA6_2 <= KW_DATETIME)||LA6_2==KW_DECIMAL||LA6_2==KW_DOUBLE||LA6_2==KW_FLOAT||LA6_2==KW_INT||LA6_2==KW_MAP||LA6_2==KW_SMALLINT||(LA6_2 >= KW_STRING && LA6_2 <= KW_STRUCT)||(LA6_2 >= KW_TIMESTAMP && LA6_2 <= KW_TINYINT)||LA6_2==KW_UNIONTYPE||LA6_2==KW_VARCHAR) ) {
									alt6=2;
								}

								else {
									if (state.backtracking>0) {state.failed=true; return retval;}
									int nvaeMark = input.mark();
									try {
										input.consume();
										NoViableAltException nvae =
											new NoViableAltException("", 6, 2, input);
										throw nvae;
									} finally {
										input.rewind(nvaeMark);
									}
								}

							}

							else {
								if (state.backtracking>0) {state.failed=true; return retval;}
								NoViableAltException nvae =
									new NoViableAltException("", 6, 0, input);
								throw nvae;
							}

							switch (alt6) {
								case 1 :
									// SelectClauseParser.g:75:23: aliasList
									{
									pushFollow(FOLLOW_aliasList_in_selectTrfmClause324);
									aliasList17=gHiveParser.aliasList();
									state._fsp--;
									if (state.failed) return retval;
									if ( state.backtracking==0 ) stream_aliasList.add(aliasList17.getTree());
									}
									break;
								case 2 :
									// SelectClauseParser.g:75:35: columnNameTypeList
									{
									pushFollow(FOLLOW_columnNameTypeList_in_selectTrfmClause328);
									columnNameTypeList18=gHiveParser.columnNameTypeList();
									state._fsp--;
									if (state.failed) return retval;
									if ( state.backtracking==0 ) stream_columnNameTypeList.add(columnNameTypeList18.getTree());
									}
									break;

							}

							RPAREN19=(Token)match(input,RPAREN,FOLLOW_RPAREN_in_selectTrfmClause331); if (state.failed) return retval; 
							if ( state.backtracking==0 ) stream_RPAREN.add(RPAREN19);

							}

							}
							break;
						case 2 :
							// SelectClauseParser.g:75:65: ( aliasList | columnNameTypeList )
							{
							// SelectClauseParser.g:75:65: ( aliasList | columnNameTypeList )
							int alt7=2;
							alt7 = dfa7.predict(input);
							switch (alt7) {
								case 1 :
									// SelectClauseParser.g:75:66: aliasList
									{
									pushFollow(FOLLOW_aliasList_in_selectTrfmClause337);
									aliasList20=gHiveParser.aliasList();
									state._fsp--;
									if (state.failed) return retval;
									if ( state.backtracking==0 ) stream_aliasList.add(aliasList20.getTree());
									}
									break;
								case 2 :
									// SelectClauseParser.g:75:78: columnNameTypeList
									{
									pushFollow(FOLLOW_columnNameTypeList_in_selectTrfmClause341);
									columnNameTypeList21=gHiveParser.columnNameTypeList();
									state._fsp--;
									if (state.failed) return retval;
									if ( state.backtracking==0 ) stream_columnNameTypeList.add(columnNameTypeList21.getTree());
									}
									break;

							}

							}
							break;

					}

					}
					break;

			}

			pushFollow(FOLLOW_rowFormat_in_selectTrfmClause353);
			outSerde=gHiveParser.rowFormat();
			state._fsp--;
			if (state.failed) return retval;
			if ( state.backtracking==0 ) stream_rowFormat.add(outSerde.getTree());
			pushFollow(FOLLOW_recordReader_in_selectTrfmClause357);
			outRec=gHiveParser.recordReader();
			state._fsp--;
			if (state.failed) return retval;
			if ( state.backtracking==0 ) stream_recordReader.add(outRec.getTree());
			// AST REWRITE
			// elements: selectExpressionList, StringLiteral, outSerde, inRec, outRec, columnNameTypeList, inSerde, aliasList
			// token labels: 
			// rule labels: inRec, outRec, inSerde, outSerde, retval
			// token list labels: 
			// rule list labels: 
			// wildcard labels: 
			if ( state.backtracking==0 ) {
			retval.tree = root_0;
			RewriteRuleSubtreeStream stream_inRec=new RewriteRuleSubtreeStream(adaptor,"rule inRec",inRec!=null?inRec.getTree():null);
			RewriteRuleSubtreeStream stream_outRec=new RewriteRuleSubtreeStream(adaptor,"rule outRec",outRec!=null?outRec.getTree():null);
			RewriteRuleSubtreeStream stream_inSerde=new RewriteRuleSubtreeStream(adaptor,"rule inSerde",inSerde!=null?inSerde.getTree():null);
			RewriteRuleSubtreeStream stream_outSerde=new RewriteRuleSubtreeStream(adaptor,"rule outSerde",outSerde!=null?outSerde.getTree():null);
			RewriteRuleSubtreeStream stream_retval=new RewriteRuleSubtreeStream(adaptor,"rule retval",retval!=null?retval.getTree():null);

			root_0 = (ASTNode)adaptor.nil();
			// 77:5: -> ^( TOK_TRANSFORM selectExpressionList $inSerde $inRec StringLiteral $outSerde $outRec ( aliasList )? ( columnNameTypeList )? )
			{
				// SelectClauseParser.g:77:8: ^( TOK_TRANSFORM selectExpressionList $inSerde $inRec StringLiteral $outSerde $outRec ( aliasList )? ( columnNameTypeList )? )
				{
				ASTNode root_1 = (ASTNode)adaptor.nil();
				root_1 = (ASTNode)adaptor.becomeRoot((ASTNode)adaptor.create(TOK_TRANSFORM, "TOK_TRANSFORM"), root_1);
				adaptor.addChild(root_1, stream_selectExpressionList.nextTree());
				adaptor.addChild(root_1, stream_inSerde.nextTree());
				adaptor.addChild(root_1, stream_inRec.nextTree());
				adaptor.addChild(root_1, stream_StringLiteral.nextNode());
				adaptor.addChild(root_1, stream_outSerde.nextTree());
				adaptor.addChild(root_1, stream_outRec.nextTree());
				// SelectClauseParser.g:77:93: ( aliasList )?
				if ( stream_aliasList.hasNext() ) {
					adaptor.addChild(root_1, stream_aliasList.nextTree());
				}
				stream_aliasList.reset();

				// SelectClauseParser.g:77:104: ( columnNameTypeList )?
				if ( stream_columnNameTypeList.hasNext() ) {
					adaptor.addChild(root_1, stream_columnNameTypeList.nextTree());
				}
				stream_columnNameTypeList.reset();

				adaptor.addChild(root_0, root_1);
				}

			}


			retval.tree = root_0;
			}

			}

			retval.stop = input.LT(-1);

			if ( state.backtracking==0 ) {
			retval.tree = (ASTNode)adaptor.rulePostProcessing(root_0);
			adaptor.setTokenBoundaries(retval.tree, retval.start, retval.stop);
			}
			if ( state.backtracking==0 ) { gParent.popMsg(state); }
		}

		catch (RecognitionException e) {
		  throw e;
		}

		finally {
			// do for sure before leaving
		}
		return retval;
	}
	// $ANTLR end "selectTrfmClause"


	public static class selectItem_return extends ParserRuleReturnScope {
		ASTNode tree;
		@Override
		public ASTNode getTree() { return tree; }
	};


	// $ANTLR start "selectItem"
	// SelectClauseParser.g:80:1: selectItem : ( ( tableAllColumns )=> tableAllColumns -> ^( TOK_SELEXPR tableAllColumns ) | ( expression ( ( ( KW_AS )? identifier ) | ( KW_AS LPAREN identifier ( COMMA identifier )* RPAREN ) )? ) -> ^( TOK_SELEXPR expression ( identifier )* ) );
	public final HiveParser_SelectClauseParser.selectItem_return selectItem() throws RecognitionException {
		HiveParser_SelectClauseParser.selectItem_return retval = new HiveParser_SelectClauseParser.selectItem_return();
		retval.start = input.LT(1);

		ASTNode root_0 = null;

		Token KW_AS24=null;
		Token KW_AS26=null;
		Token LPAREN27=null;
		Token COMMA29=null;
		Token RPAREN31=null;
		ParserRuleReturnScope tableAllColumns22 =null;
		ParserRuleReturnScope expression23 =null;
		ParserRuleReturnScope identifier25 =null;
		ParserRuleReturnScope identifier28 =null;
		ParserRuleReturnScope identifier30 =null;

		ASTNode KW_AS24_tree=null;
		ASTNode KW_AS26_tree=null;
		ASTNode LPAREN27_tree=null;
		ASTNode COMMA29_tree=null;
		ASTNode RPAREN31_tree=null;
		RewriteRuleTokenStream stream_COMMA=new RewriteRuleTokenStream(adaptor,"token COMMA");
		RewriteRuleTokenStream stream_LPAREN=new RewriteRuleTokenStream(adaptor,"token LPAREN");
		RewriteRuleTokenStream stream_RPAREN=new RewriteRuleTokenStream(adaptor,"token RPAREN");
		RewriteRuleTokenStream stream_KW_AS=new RewriteRuleTokenStream(adaptor,"token KW_AS");
		RewriteRuleSubtreeStream stream_identifier=new RewriteRuleSubtreeStream(adaptor,"rule identifier");
		RewriteRuleSubtreeStream stream_expression=new RewriteRuleSubtreeStream(adaptor,"rule expression");
		RewriteRuleSubtreeStream stream_tableAllColumns=new RewriteRuleSubtreeStream(adaptor,"rule tableAllColumns");

		 gParent.pushMsg("selection target", state); 
		try {
			// SelectClauseParser.g:83:5: ( ( tableAllColumns )=> tableAllColumns -> ^( TOK_SELEXPR tableAllColumns ) | ( expression ( ( ( KW_AS )? identifier ) | ( KW_AS LPAREN identifier ( COMMA identifier )* RPAREN ) )? ) -> ^( TOK_SELEXPR expression ( identifier )* ) )
			int alt13=2;
			alt13 = dfa13.predict(input);
			switch (alt13) {
				case 1 :
					// SelectClauseParser.g:84:5: ( tableAllColumns )=> tableAllColumns
					{
					pushFollow(FOLLOW_tableAllColumns_in_selectItem426);
					tableAllColumns22=gHiveParser.tableAllColumns();
					state._fsp--;
					if (state.failed) return retval;
					if ( state.backtracking==0 ) stream_tableAllColumns.add(tableAllColumns22.getTree());
					// AST REWRITE
					// elements: tableAllColumns
					// token labels: 
					// rule labels: retval
					// token list labels: 
					// rule list labels: 
					// wildcard labels: 
					if ( state.backtracking==0 ) {
					retval.tree = root_0;
					RewriteRuleSubtreeStream stream_retval=new RewriteRuleSubtreeStream(adaptor,"rule retval",retval!=null?retval.getTree():null);

					root_0 = (ASTNode)adaptor.nil();
					// 84:42: -> ^( TOK_SELEXPR tableAllColumns )
					{
						// SelectClauseParser.g:84:45: ^( TOK_SELEXPR tableAllColumns )
						{
						ASTNode root_1 = (ASTNode)adaptor.nil();
						root_1 = (ASTNode)adaptor.becomeRoot((ASTNode)adaptor.create(TOK_SELEXPR, "TOK_SELEXPR"), root_1);
						adaptor.addChild(root_1, stream_tableAllColumns.nextTree());
						adaptor.addChild(root_0, root_1);
						}

					}


					retval.tree = root_0;
					}

					}
					break;
				case 2 :
					// SelectClauseParser.g:86:5: ( expression ( ( ( KW_AS )? identifier ) | ( KW_AS LPAREN identifier ( COMMA identifier )* RPAREN ) )? )
					{
					// SelectClauseParser.g:86:5: ( expression ( ( ( KW_AS )? identifier ) | ( KW_AS LPAREN identifier ( COMMA identifier )* RPAREN ) )? )
					// SelectClauseParser.g:86:7: expression ( ( ( KW_AS )? identifier ) | ( KW_AS LPAREN identifier ( COMMA identifier )* RPAREN ) )?
					{
					pushFollow(FOLLOW_expression_in_selectItem448);
					expression23=gHiveParser.expression();
					state._fsp--;
					if (state.failed) return retval;
					if ( state.backtracking==0 ) stream_expression.add(expression23.getTree());
					// SelectClauseParser.g:87:7: ( ( ( KW_AS )? identifier ) | ( KW_AS LPAREN identifier ( COMMA identifier )* RPAREN ) )?
					int alt12=3;
					alt12 = dfa12.predict(input);
					switch (alt12) {
						case 1 :
							// SelectClauseParser.g:87:8: ( ( KW_AS )? identifier )
							{
							// SelectClauseParser.g:87:8: ( ( KW_AS )? identifier )
							// SelectClauseParser.g:87:9: ( KW_AS )? identifier
							{
							// SelectClauseParser.g:87:9: ( KW_AS )?
							int alt10=2;
							int LA10_0 = input.LA(1);
							if ( (LA10_0==KW_AS) ) {
								alt10=1;
							}
							switch (alt10) {
								case 1 :
									// SelectClauseParser.g:87:9: KW_AS
									{
									KW_AS24=(Token)match(input,KW_AS,FOLLOW_KW_AS_in_selectItem458); if (state.failed) return retval; 
									if ( state.backtracking==0 ) stream_KW_AS.add(KW_AS24);

									}
									break;

							}

							pushFollow(FOLLOW_identifier_in_selectItem461);
							identifier25=gHiveParser.identifier();
							state._fsp--;
							if (state.failed) return retval;
							if ( state.backtracking==0 ) stream_identifier.add(identifier25.getTree());
							}

							}
							break;
						case 2 :
							// SelectClauseParser.g:87:30: ( KW_AS LPAREN identifier ( COMMA identifier )* RPAREN )
							{
							// SelectClauseParser.g:87:30: ( KW_AS LPAREN identifier ( COMMA identifier )* RPAREN )
							// SelectClauseParser.g:87:31: KW_AS LPAREN identifier ( COMMA identifier )* RPAREN
							{
							KW_AS26=(Token)match(input,KW_AS,FOLLOW_KW_AS_in_selectItem467); if (state.failed) return retval; 
							if ( state.backtracking==0 ) stream_KW_AS.add(KW_AS26);

							LPAREN27=(Token)match(input,LPAREN,FOLLOW_LPAREN_in_selectItem469); if (state.failed) return retval; 
							if ( state.backtracking==0 ) stream_LPAREN.add(LPAREN27);

							pushFollow(FOLLOW_identifier_in_selectItem471);
							identifier28=gHiveParser.identifier();
							state._fsp--;
							if (state.failed) return retval;
							if ( state.backtracking==0 ) stream_identifier.add(identifier28.getTree());
							// SelectClauseParser.g:87:55: ( COMMA identifier )*
							loop11:
							while (true) {
								int alt11=2;
								int LA11_0 = input.LA(1);
								if ( (LA11_0==COMMA) ) {
									alt11=1;
								}

								switch (alt11) {
								case 1 :
									// SelectClauseParser.g:87:56: COMMA identifier
									{
									COMMA29=(Token)match(input,COMMA,FOLLOW_COMMA_in_selectItem474); if (state.failed) return retval; 
									if ( state.backtracking==0 ) stream_COMMA.add(COMMA29);

									pushFollow(FOLLOW_identifier_in_selectItem476);
									identifier30=gHiveParser.identifier();
									state._fsp--;
									if (state.failed) return retval;
									if ( state.backtracking==0 ) stream_identifier.add(identifier30.getTree());
									}
									break;

								default :
									break loop11;
								}
							}

							RPAREN31=(Token)match(input,RPAREN,FOLLOW_RPAREN_in_selectItem480); if (state.failed) return retval; 
							if ( state.backtracking==0 ) stream_RPAREN.add(RPAREN31);

							}

							}
							break;

					}

					}

					// AST REWRITE
					// elements: expression, identifier
					// token labels: 
					// rule labels: retval
					// token list labels: 
					// rule list labels: 
					// wildcard labels: 
					if ( state.backtracking==0 ) {
					retval.tree = root_0;
					RewriteRuleSubtreeStream stream_retval=new RewriteRuleSubtreeStream(adaptor,"rule retval",retval!=null?retval.getTree():null);

					root_0 = (ASTNode)adaptor.nil();
					// 88:7: -> ^( TOK_SELEXPR expression ( identifier )* )
					{
						// SelectClauseParser.g:88:10: ^( TOK_SELEXPR expression ( identifier )* )
						{
						ASTNode root_1 = (ASTNode)adaptor.nil();
						root_1 = (ASTNode)adaptor.becomeRoot((ASTNode)adaptor.create(TOK_SELEXPR, "TOK_SELEXPR"), root_1);
						adaptor.addChild(root_1, stream_expression.nextTree());
						// SelectClauseParser.g:88:35: ( identifier )*
						while ( stream_identifier.hasNext() ) {
							adaptor.addChild(root_1, stream_identifier.nextTree());
						}
						stream_identifier.reset();

						adaptor.addChild(root_0, root_1);
						}

					}


					retval.tree = root_0;
					}

					}
					break;

			}
			retval.stop = input.LT(-1);

			if ( state.backtracking==0 ) {
			retval.tree = (ASTNode)adaptor.rulePostProcessing(root_0);
			adaptor.setTokenBoundaries(retval.tree, retval.start, retval.stop);
			}
			if ( state.backtracking==0 ) { gParent.popMsg(state); }
		}

		catch (RecognitionException e) {
		  throw e;
		}

		finally {
			// do for sure before leaving
		}
		return retval;
	}
	// $ANTLR end "selectItem"


	public static class trfmClause_return extends ParserRuleReturnScope {
		ASTNode tree;
		@Override
		public ASTNode getTree() { return tree; }
	};


	// $ANTLR start "trfmClause"
	// SelectClauseParser.g:91:1: trfmClause : ( KW_MAP selectExpressionList | KW_REDUCE selectExpressionList ) inSerde= rowFormat inRec= recordWriter KW_USING StringLiteral ( KW_AS ( ( LPAREN ( aliasList | columnNameTypeList ) RPAREN ) | ( aliasList | columnNameTypeList ) ) )? outSerde= rowFormat outRec= recordReader -> ^( TOK_TRANSFORM selectExpressionList $inSerde $inRec StringLiteral $outSerde $outRec ( aliasList )? ( columnNameTypeList )? ) ;
	public final HiveParser_SelectClauseParser.trfmClause_return trfmClause() throws RecognitionException {
		HiveParser_SelectClauseParser.trfmClause_return retval = new HiveParser_SelectClauseParser.trfmClause_return();
		retval.start = input.LT(1);

		ASTNode root_0 = null;

		Token KW_MAP32=null;
		Token KW_REDUCE34=null;
		Token KW_USING36=null;
		Token StringLiteral37=null;
		Token KW_AS38=null;
		Token LPAREN39=null;
		Token RPAREN42=null;
		ParserRuleReturnScope inSerde =null;
		ParserRuleReturnScope inRec =null;
		ParserRuleReturnScope outSerde =null;
		ParserRuleReturnScope outRec =null;
		ParserRuleReturnScope selectExpressionList33 =null;
		ParserRuleReturnScope selectExpressionList35 =null;
		ParserRuleReturnScope aliasList40 =null;
		ParserRuleReturnScope columnNameTypeList41 =null;
		ParserRuleReturnScope aliasList43 =null;
		ParserRuleReturnScope columnNameTypeList44 =null;

		ASTNode KW_MAP32_tree=null;
		ASTNode KW_REDUCE34_tree=null;
		ASTNode KW_USING36_tree=null;
		ASTNode StringLiteral37_tree=null;
		ASTNode KW_AS38_tree=null;
		ASTNode LPAREN39_tree=null;
		ASTNode RPAREN42_tree=null;
		RewriteRuleTokenStream stream_StringLiteral=new RewriteRuleTokenStream(adaptor,"token StringLiteral");
		RewriteRuleTokenStream stream_KW_REDUCE=new RewriteRuleTokenStream(adaptor,"token KW_REDUCE");
		RewriteRuleTokenStream stream_KW_USING=new RewriteRuleTokenStream(adaptor,"token KW_USING");
		RewriteRuleTokenStream stream_KW_MAP=new RewriteRuleTokenStream(adaptor,"token KW_MAP");
		RewriteRuleTokenStream stream_LPAREN=new RewriteRuleTokenStream(adaptor,"token LPAREN");
		RewriteRuleTokenStream stream_RPAREN=new RewriteRuleTokenStream(adaptor,"token RPAREN");
		RewriteRuleTokenStream stream_KW_AS=new RewriteRuleTokenStream(adaptor,"token KW_AS");
		RewriteRuleSubtreeStream stream_aliasList=new RewriteRuleSubtreeStream(adaptor,"rule aliasList");
		RewriteRuleSubtreeStream stream_rowFormat=new RewriteRuleSubtreeStream(adaptor,"rule rowFormat");
		RewriteRuleSubtreeStream stream_columnNameTypeList=new RewriteRuleSubtreeStream(adaptor,"rule columnNameTypeList");
		RewriteRuleSubtreeStream stream_recordReader=new RewriteRuleSubtreeStream(adaptor,"rule recordReader");
		RewriteRuleSubtreeStream stream_selectExpressionList=new RewriteRuleSubtreeStream(adaptor,"rule selectExpressionList");
		RewriteRuleSubtreeStream stream_recordWriter=new RewriteRuleSubtreeStream(adaptor,"rule recordWriter");

		 gParent.pushMsg("transform clause", state); 
		try {
			// SelectClauseParser.g:94:5: ( ( KW_MAP selectExpressionList | KW_REDUCE selectExpressionList ) inSerde= rowFormat inRec= recordWriter KW_USING StringLiteral ( KW_AS ( ( LPAREN ( aliasList | columnNameTypeList ) RPAREN ) | ( aliasList | columnNameTypeList ) ) )? outSerde= rowFormat outRec= recordReader -> ^( TOK_TRANSFORM selectExpressionList $inSerde $inRec StringLiteral $outSerde $outRec ( aliasList )? ( columnNameTypeList )? ) )
			// SelectClauseParser.g:95:5: ( KW_MAP selectExpressionList | KW_REDUCE selectExpressionList ) inSerde= rowFormat inRec= recordWriter KW_USING StringLiteral ( KW_AS ( ( LPAREN ( aliasList | columnNameTypeList ) RPAREN ) | ( aliasList | columnNameTypeList ) ) )? outSerde= rowFormat outRec= recordReader
			{
			// SelectClauseParser.g:95:5: ( KW_MAP selectExpressionList | KW_REDUCE selectExpressionList )
			int alt14=2;
			int LA14_0 = input.LA(1);
			if ( (LA14_0==KW_MAP) ) {
				alt14=1;
			}
			else if ( (LA14_0==KW_REDUCE) ) {
				alt14=2;
			}

			else {
				if (state.backtracking>0) {state.failed=true; return retval;}
				NoViableAltException nvae =
					new NoViableAltException("", 14, 0, input);
				throw nvae;
			}

			switch (alt14) {
				case 1 :
					// SelectClauseParser.g:95:9: KW_MAP selectExpressionList
					{
					KW_MAP32=(Token)match(input,KW_MAP,FOLLOW_KW_MAP_in_trfmClause535); if (state.failed) return retval; 
					if ( state.backtracking==0 ) stream_KW_MAP.add(KW_MAP32);

					pushFollow(FOLLOW_selectExpressionList_in_trfmClause540);
					selectExpressionList33=selectExpressionList();
					state._fsp--;
					if (state.failed) return retval;
					if ( state.backtracking==0 ) stream_selectExpressionList.add(selectExpressionList33.getTree());
					}
					break;
				case 2 :
					// SelectClauseParser.g:96:9: KW_REDUCE selectExpressionList
					{
					KW_REDUCE34=(Token)match(input,KW_REDUCE,FOLLOW_KW_REDUCE_in_trfmClause550); if (state.failed) return retval; 
					if ( state.backtracking==0 ) stream_KW_REDUCE.add(KW_REDUCE34);

					pushFollow(FOLLOW_selectExpressionList_in_trfmClause552);
					selectExpressionList35=selectExpressionList();
					state._fsp--;
					if (state.failed) return retval;
					if ( state.backtracking==0 ) stream_selectExpressionList.add(selectExpressionList35.getTree());
					}
					break;

			}

			pushFollow(FOLLOW_rowFormat_in_trfmClause562);
			inSerde=gHiveParser.rowFormat();
			state._fsp--;
			if (state.failed) return retval;
			if ( state.backtracking==0 ) stream_rowFormat.add(inSerde.getTree());
			pushFollow(FOLLOW_recordWriter_in_trfmClause566);
			inRec=gHiveParser.recordWriter();
			state._fsp--;
			if (state.failed) return retval;
			if ( state.backtracking==0 ) stream_recordWriter.add(inRec.getTree());
			KW_USING36=(Token)match(input,KW_USING,FOLLOW_KW_USING_in_trfmClause572); if (state.failed) return retval; 
			if ( state.backtracking==0 ) stream_KW_USING.add(KW_USING36);

			StringLiteral37=(Token)match(input,StringLiteral,FOLLOW_StringLiteral_in_trfmClause574); if (state.failed) return retval; 
			if ( state.backtracking==0 ) stream_StringLiteral.add(StringLiteral37);

			// SelectClauseParser.g:99:5: ( KW_AS ( ( LPAREN ( aliasList | columnNameTypeList ) RPAREN ) | ( aliasList | columnNameTypeList ) ) )?
			int alt18=2;
			int LA18_0 = input.LA(1);
			if ( (LA18_0==KW_AS) ) {
				alt18=1;
			}
			switch (alt18) {
				case 1 :
					// SelectClauseParser.g:99:7: KW_AS ( ( LPAREN ( aliasList | columnNameTypeList ) RPAREN ) | ( aliasList | columnNameTypeList ) )
					{
					KW_AS38=(Token)match(input,KW_AS,FOLLOW_KW_AS_in_trfmClause582); if (state.failed) return retval; 
					if ( state.backtracking==0 ) stream_KW_AS.add(KW_AS38);

					// SelectClauseParser.g:99:13: ( ( LPAREN ( aliasList | columnNameTypeList ) RPAREN ) | ( aliasList | columnNameTypeList ) )
					int alt17=2;
					int LA17_0 = input.LA(1);
					if ( (LA17_0==LPAREN) ) {
						alt17=1;
					}
					else if ( (LA17_0==Identifier||(LA17_0 >= KW_ABORT && LA17_0 <= KW_AFTER)||LA17_0==KW_ANALYZE||LA17_0==KW_ARCHIVE||LA17_0==KW_ASC||(LA17_0 >= KW_AUTOCOMMIT && LA17_0 <= KW_BEFORE)||(LA17_0 >= KW_BUCKET && LA17_0 <= KW_BUCKETS)||(LA17_0 >= KW_CACHE && LA17_0 <= KW_CASCADE)||LA17_0==KW_CHANGE||(LA17_0 >= KW_CLUSTER && LA17_0 <= KW_COLLECTION)||(LA17_0 >= KW_COLUMNS && LA17_0 <= KW_COMMENT)||(LA17_0 >= KW_COMPACT && LA17_0 <= KW_CONCATENATE)||LA17_0==KW_CONTINUE||LA17_0==KW_DATA||LA17_0==KW_DATABASES||(LA17_0 >= KW_DATETIME && LA17_0 <= KW_DBPROPERTIES)||(LA17_0 >= KW_DEFERRED && LA17_0 <= KW_DEFINED)||(LA17_0 >= KW_DELIMITED && LA17_0 <= KW_DESC)||(LA17_0 >= KW_DETAIL && LA17_0 <= KW_DISABLE)||LA17_0==KW_DISTRIBUTE||LA17_0==KW_DOW||(LA17_0 >= KW_DUMP && LA17_0 <= KW_ELEM_TYPE)||LA17_0==KW_ENABLE||LA17_0==KW_ESCAPED||LA17_0==KW_EXCLUSIVE||(LA17_0 >= KW_EXPLAIN && LA17_0 <= KW_EXPRESSION)||(LA17_0 >= KW_FIELDS && LA17_0 <= KW_FIRST)||(LA17_0 >= KW_FORMAT && LA17_0 <= KW_FORMATTED)||LA17_0==KW_FUNCTIONS||(LA17_0 >= KW_HOUR && LA17_0 <= KW_IDXPROPERTIES)||(LA17_0 >= KW_INDEX && LA17_0 <= KW_INDEXES)||(LA17_0 >= KW_INPATH && LA17_0 <= KW_INPUTFORMAT)||(LA17_0 >= KW_ISOLATION && LA17_0 <= KW_JAR)||(LA17_0 >= KW_KEY && LA17_0 <= KW_LAST)||LA17_0==KW_LEVEL||(LA17_0 >= KW_LIMIT && LA17_0 <= KW_LOAD)||(LA17_0 >= KW_LOCATION && LA17_0 <= KW_LONG)||(LA17_0 >= KW_MAPJOIN && LA17_0 <= KW_MATERIALIZED)||LA17_0==KW_METADATA||(LA17_0 >= KW_MINUTE && LA17_0 <= KW_MONTH)||LA17_0==KW_MSCK||(LA17_0 >= KW_NORELY && LA17_0 <= KW_NOSCAN)||LA17_0==KW_NOVALIDATE||LA17_0==KW_NULLS||LA17_0==KW_OFFSET||(LA17_0 >= KW_OPERATOR && LA17_0 <= KW_OPTION)||(LA17_0 >= KW_OUTPUTDRIVER && LA17_0 <= KW_OUTPUTFORMAT)||(LA17_0 >= KW_OVERWRITE && LA17_0 <= KW_OWNER)||(LA17_0 >= KW_PARTITIONED && LA17_0 <= KW_PARTITIONS)||LA17_0==KW_PLUS||LA17_0==KW_PRETTY||LA17_0==KW_PRINCIPALS||(LA17_0 >= KW_PURGE && LA17_0 <= KW_QUARTER)||LA17_0==KW_READ||(LA17_0 >= KW_REBUILD && LA17_0 <= KW_RECORDWRITER)||(LA17_0 >= KW_RELOAD && LA17_0 <= KW_RESTRICT)||LA17_0==KW_REWRITE||(LA17_0 >= KW_ROLE && LA17_0 <= KW_ROLES)||(LA17_0 >= KW_SCHEMA && LA17_0 <= KW_SECOND)||(LA17_0 >= KW_SEMI && LA17_0 <= KW_SERVER)||(LA17_0 >= KW_SETS && LA17_0 <= KW_SKEWED)||(LA17_0 >= KW_SNAPSHOT && LA17_0 <= KW_SSL)||(LA17_0 >= KW_STATISTICS && LA17_0 <= KW_SUMMARY)||LA17_0==KW_TABLES||(LA17_0 >= KW_TBLPROPERTIES && LA17_0 <= KW_TERMINATED)||LA17_0==KW_TINYINT||(LA17_0 >= KW_TOUCH && LA17_0 <= KW_TRANSACTIONS)||LA17_0==KW_UNARCHIVE||LA17_0==KW_UNDO||LA17_0==KW_UNIONTYPE||(LA17_0 >= KW_UNLOCK && LA17_0 <= KW_UNSIGNED)||(LA17_0 >= KW_URI && LA17_0 <= KW_USE)||(LA17_0 >= KW_UTC && LA17_0 <= KW_VALIDATE)||LA17_0==KW_VALUE_TYPE||(LA17_0 >= KW_VECTORIZATION && LA17_0 <= KW_WEEK)||LA17_0==KW_WHILE||(LA17_0 >= KW_WORK && LA17_0 <= KW_YEAR)||LA17_0==KW_BATCH||LA17_0==KW_DAYOFWEEK||LA17_0==KW_HOLD_DDLTIME||LA17_0==KW_IGNORE||LA17_0==KW_NO_DROP||LA17_0==KW_OFFLINE||LA17_0==KW_PROTECTION||LA17_0==KW_READONLY) ) {
						alt17=2;
					}

					else {
						if (state.backtracking>0) {state.failed=true; return retval;}
						NoViableAltException nvae =
							new NoViableAltException("", 17, 0, input);
						throw nvae;
					}

					switch (alt17) {
						case 1 :
							// SelectClauseParser.g:99:14: ( LPAREN ( aliasList | columnNameTypeList ) RPAREN )
							{
							// SelectClauseParser.g:99:14: ( LPAREN ( aliasList | columnNameTypeList ) RPAREN )
							// SelectClauseParser.g:99:15: LPAREN ( aliasList | columnNameTypeList ) RPAREN
							{
							LPAREN39=(Token)match(input,LPAREN,FOLLOW_LPAREN_in_trfmClause586); if (state.failed) return retval; 
							if ( state.backtracking==0 ) stream_LPAREN.add(LPAREN39);

							// SelectClauseParser.g:99:22: ( aliasList | columnNameTypeList )
							int alt15=2;
							int LA15_0 = input.LA(1);
							if ( (LA15_0==Identifier) ) {
								int LA15_1 = input.LA(2);
								if ( (LA15_1==COMMA||LA15_1==RPAREN) ) {
									alt15=1;
								}
								else if ( (LA15_1==KW_ARRAY||(LA15_1 >= KW_BIGINT && LA15_1 <= KW_BOOLEAN)||LA15_1==KW_CHAR||(LA15_1 >= KW_DATE && LA15_1 <= KW_DATETIME)||LA15_1==KW_DECIMAL||LA15_1==KW_DOUBLE||LA15_1==KW_FLOAT||LA15_1==KW_INT||LA15_1==KW_MAP||LA15_1==KW_SMALLINT||(LA15_1 >= KW_STRING && LA15_1 <= KW_STRUCT)||(LA15_1 >= KW_TIMESTAMP && LA15_1 <= KW_TINYINT)||LA15_1==KW_UNIONTYPE||LA15_1==KW_VARCHAR) ) {
									alt15=2;
								}

								else {
									if (state.backtracking>0) {state.failed=true; return retval;}
									int nvaeMark = input.mark();
									try {
										input.consume();
										NoViableAltException nvae =
											new NoViableAltException("", 15, 1, input);
										throw nvae;
									} finally {
										input.rewind(nvaeMark);
									}
								}

							}
							else if ( ((LA15_0 >= KW_ABORT && LA15_0 <= KW_AFTER)||LA15_0==KW_ANALYZE||LA15_0==KW_ARCHIVE||LA15_0==KW_ASC||(LA15_0 >= KW_AUTOCOMMIT && LA15_0 <= KW_BEFORE)||(LA15_0 >= KW_BUCKET && LA15_0 <= KW_BUCKETS)||(LA15_0 >= KW_CACHE && LA15_0 <= KW_CASCADE)||LA15_0==KW_CHANGE||(LA15_0 >= KW_CLUSTER && LA15_0 <= KW_COLLECTION)||(LA15_0 >= KW_COLUMNS && LA15_0 <= KW_COMMENT)||(LA15_0 >= KW_COMPACT && LA15_0 <= KW_CONCATENATE)||LA15_0==KW_CONTINUE||LA15_0==KW_DATA||LA15_0==KW_DATABASES||(LA15_0 >= KW_DATETIME && LA15_0 <= KW_DBPROPERTIES)||(LA15_0 >= KW_DEFERRED && LA15_0 <= KW_DEFINED)||(LA15_0 >= KW_DELIMITED && LA15_0 <= KW_DESC)||(LA15_0 >= KW_DETAIL && LA15_0 <= KW_DISABLE)||LA15_0==KW_DISTRIBUTE||LA15_0==KW_DOW||(LA15_0 >= KW_DUMP && LA15_0 <= KW_ELEM_TYPE)||LA15_0==KW_ENABLE||LA15_0==KW_ESCAPED||LA15_0==KW_EXCLUSIVE||(LA15_0 >= KW_EXPLAIN && LA15_0 <= KW_EXPRESSION)||(LA15_0 >= KW_FIELDS && LA15_0 <= KW_FIRST)||(LA15_0 >= KW_FORMAT && LA15_0 <= KW_FORMATTED)||LA15_0==KW_FUNCTIONS||(LA15_0 >= KW_HOUR && LA15_0 <= KW_IDXPROPERTIES)||(LA15_0 >= KW_INDEX && LA15_0 <= KW_INDEXES)||(LA15_0 >= KW_INPATH && LA15_0 <= KW_INPUTFORMAT)||(LA15_0 >= KW_ISOLATION && LA15_0 <= KW_JAR)||(LA15_0 >= KW_KEY && LA15_0 <= KW_LAST)||LA15_0==KW_LEVEL||(LA15_0 >= KW_LIMIT && LA15_0 <= KW_LOAD)||(LA15_0 >= KW_LOCATION && LA15_0 <= KW_LONG)||(LA15_0 >= KW_MAPJOIN && LA15_0 <= KW_MATERIALIZED)||LA15_0==KW_METADATA||(LA15_0 >= KW_MINUTE && LA15_0 <= KW_MONTH)||LA15_0==KW_MSCK||(LA15_0 >= KW_NORELY && LA15_0 <= KW_NOSCAN)||LA15_0==KW_NOVALIDATE||LA15_0==KW_NULLS||LA15_0==KW_OFFSET||(LA15_0 >= KW_OPERATOR && LA15_0 <= KW_OPTION)||(LA15_0 >= KW_OUTPUTDRIVER && LA15_0 <= KW_OUTPUTFORMAT)||(LA15_0 >= KW_OVERWRITE && LA15_0 <= KW_OWNER)||(LA15_0 >= KW_PARTITIONED && LA15_0 <= KW_PARTITIONS)||LA15_0==KW_PLUS||LA15_0==KW_PRETTY||LA15_0==KW_PRINCIPALS||(LA15_0 >= KW_PURGE && LA15_0 <= KW_QUARTER)||LA15_0==KW_READ||(LA15_0 >= KW_REBUILD && LA15_0 <= KW_RECORDWRITER)||(LA15_0 >= KW_RELOAD && LA15_0 <= KW_RESTRICT)||LA15_0==KW_REWRITE||(LA15_0 >= KW_ROLE && LA15_0 <= KW_ROLES)||(LA15_0 >= KW_SCHEMA && LA15_0 <= KW_SECOND)||(LA15_0 >= KW_SEMI && LA15_0 <= KW_SERVER)||(LA15_0 >= KW_SETS && LA15_0 <= KW_SKEWED)||(LA15_0 >= KW_SNAPSHOT && LA15_0 <= KW_SSL)||(LA15_0 >= KW_STATISTICS && LA15_0 <= KW_SUMMARY)||LA15_0==KW_TABLES||(LA15_0 >= KW_TBLPROPERTIES && LA15_0 <= KW_TERMINATED)||LA15_0==KW_TINYINT||(LA15_0 >= KW_TOUCH && LA15_0 <= KW_TRANSACTIONS)||LA15_0==KW_UNARCHIVE||LA15_0==KW_UNDO||LA15_0==KW_UNIONTYPE||(LA15_0 >= KW_UNLOCK && LA15_0 <= KW_UNSIGNED)||(LA15_0 >= KW_URI && LA15_0 <= KW_USE)||(LA15_0 >= KW_UTC && LA15_0 <= KW_VALIDATE)||LA15_0==KW_VALUE_TYPE||(LA15_0 >= KW_VECTORIZATION && LA15_0 <= KW_WEEK)||LA15_0==KW_WHILE||(LA15_0 >= KW_WORK && LA15_0 <= KW_YEAR)||LA15_0==KW_BATCH||LA15_0==KW_DAYOFWEEK||LA15_0==KW_HOLD_DDLTIME||LA15_0==KW_IGNORE||LA15_0==KW_NO_DROP||LA15_0==KW_OFFLINE||LA15_0==KW_PROTECTION||LA15_0==KW_READONLY) ) {
								int LA15_2 = input.LA(2);
								if ( (LA15_2==COMMA||LA15_2==RPAREN) ) {
									alt15=1;
								}
								else if ( (LA15_2==KW_ARRAY||(LA15_2 >= KW_BIGINT && LA15_2 <= KW_BOOLEAN)||LA15_2==KW_CHAR||(LA15_2 >= KW_DATE && LA15_2 <= KW_DATETIME)||LA15_2==KW_DECIMAL||LA15_2==KW_DOUBLE||LA15_2==KW_FLOAT||LA15_2==KW_INT||LA15_2==KW_MAP||LA15_2==KW_SMALLINT||(LA15_2 >= KW_STRING && LA15_2 <= KW_STRUCT)||(LA15_2 >= KW_TIMESTAMP && LA15_2 <= KW_TINYINT)||LA15_2==KW_UNIONTYPE||LA15_2==KW_VARCHAR) ) {
									alt15=2;
								}

								else {
									if (state.backtracking>0) {state.failed=true; return retval;}
									int nvaeMark = input.mark();
									try {
										input.consume();
										NoViableAltException nvae =
											new NoViableAltException("", 15, 2, input);
										throw nvae;
									} finally {
										input.rewind(nvaeMark);
									}
								}

							}

							else {
								if (state.backtracking>0) {state.failed=true; return retval;}
								NoViableAltException nvae =
									new NoViableAltException("", 15, 0, input);
								throw nvae;
							}

							switch (alt15) {
								case 1 :
									// SelectClauseParser.g:99:23: aliasList
									{
									pushFollow(FOLLOW_aliasList_in_trfmClause589);
									aliasList40=gHiveParser.aliasList();
									state._fsp--;
									if (state.failed) return retval;
									if ( state.backtracking==0 ) stream_aliasList.add(aliasList40.getTree());
									}
									break;
								case 2 :
									// SelectClauseParser.g:99:35: columnNameTypeList
									{
									pushFollow(FOLLOW_columnNameTypeList_in_trfmClause593);
									columnNameTypeList41=gHiveParser.columnNameTypeList();
									state._fsp--;
									if (state.failed) return retval;
									if ( state.backtracking==0 ) stream_columnNameTypeList.add(columnNameTypeList41.getTree());
									}
									break;

							}

							RPAREN42=(Token)match(input,RPAREN,FOLLOW_RPAREN_in_trfmClause596); if (state.failed) return retval; 
							if ( state.backtracking==0 ) stream_RPAREN.add(RPAREN42);

							}

							}
							break;
						case 2 :
							// SelectClauseParser.g:99:65: ( aliasList | columnNameTypeList )
							{
							// SelectClauseParser.g:99:65: ( aliasList | columnNameTypeList )
							int alt16=2;
							alt16 = dfa16.predict(input);
							switch (alt16) {
								case 1 :
									// SelectClauseParser.g:99:66: aliasList
									{
									pushFollow(FOLLOW_aliasList_in_trfmClause602);
									aliasList43=gHiveParser.aliasList();
									state._fsp--;
									if (state.failed) return retval;
									if ( state.backtracking==0 ) stream_aliasList.add(aliasList43.getTree());
									}
									break;
								case 2 :
									// SelectClauseParser.g:99:78: columnNameTypeList
									{
									pushFollow(FOLLOW_columnNameTypeList_in_trfmClause606);
									columnNameTypeList44=gHiveParser.columnNameTypeList();
									state._fsp--;
									if (state.failed) return retval;
									if ( state.backtracking==0 ) stream_columnNameTypeList.add(columnNameTypeList44.getTree());
									}
									break;

							}

							}
							break;

					}

					}
					break;

			}

			pushFollow(FOLLOW_rowFormat_in_trfmClause618);
			outSerde=gHiveParser.rowFormat();
			state._fsp--;
			if (state.failed) return retval;
			if ( state.backtracking==0 ) stream_rowFormat.add(outSerde.getTree());
			pushFollow(FOLLOW_recordReader_in_trfmClause622);
			outRec=gHiveParser.recordReader();
			state._fsp--;
			if (state.failed) return retval;
			if ( state.backtracking==0 ) stream_recordReader.add(outRec.getTree());
			// AST REWRITE
			// elements: inSerde, StringLiteral, selectExpressionList, outRec, outSerde, inRec, aliasList, columnNameTypeList
			// token labels: 
			// rule labels: inRec, outRec, inSerde, outSerde, retval
			// token list labels: 
			// rule list labels: 
			// wildcard labels: 
			if ( state.backtracking==0 ) {
			retval.tree = root_0;
			RewriteRuleSubtreeStream stream_inRec=new RewriteRuleSubtreeStream(adaptor,"rule inRec",inRec!=null?inRec.getTree():null);
			RewriteRuleSubtreeStream stream_outRec=new RewriteRuleSubtreeStream(adaptor,"rule outRec",outRec!=null?outRec.getTree():null);
			RewriteRuleSubtreeStream stream_inSerde=new RewriteRuleSubtreeStream(adaptor,"rule inSerde",inSerde!=null?inSerde.getTree():null);
			RewriteRuleSubtreeStream stream_outSerde=new RewriteRuleSubtreeStream(adaptor,"rule outSerde",outSerde!=null?outSerde.getTree():null);
			RewriteRuleSubtreeStream stream_retval=new RewriteRuleSubtreeStream(adaptor,"rule retval",retval!=null?retval.getTree():null);

			root_0 = (ASTNode)adaptor.nil();
			// 101:5: -> ^( TOK_TRANSFORM selectExpressionList $inSerde $inRec StringLiteral $outSerde $outRec ( aliasList )? ( columnNameTypeList )? )
			{
				// SelectClauseParser.g:101:8: ^( TOK_TRANSFORM selectExpressionList $inSerde $inRec StringLiteral $outSerde $outRec ( aliasList )? ( columnNameTypeList )? )
				{
				ASTNode root_1 = (ASTNode)adaptor.nil();
				root_1 = (ASTNode)adaptor.becomeRoot((ASTNode)adaptor.create(TOK_TRANSFORM, "TOK_TRANSFORM"), root_1);
				adaptor.addChild(root_1, stream_selectExpressionList.nextTree());
				adaptor.addChild(root_1, stream_inSerde.nextTree());
				adaptor.addChild(root_1, stream_inRec.nextTree());
				adaptor.addChild(root_1, stream_StringLiteral.nextNode());
				adaptor.addChild(root_1, stream_outSerde.nextTree());
				adaptor.addChild(root_1, stream_outRec.nextTree());
				// SelectClauseParser.g:101:93: ( aliasList )?
				if ( stream_aliasList.hasNext() ) {
					adaptor.addChild(root_1, stream_aliasList.nextTree());
				}
				stream_aliasList.reset();

				// SelectClauseParser.g:101:104: ( columnNameTypeList )?
				if ( stream_columnNameTypeList.hasNext() ) {
					adaptor.addChild(root_1, stream_columnNameTypeList.nextTree());
				}
				stream_columnNameTypeList.reset();

				adaptor.addChild(root_0, root_1);
				}

			}


			retval.tree = root_0;
			}

			}

			retval.stop = input.LT(-1);

			if ( state.backtracking==0 ) {
			retval.tree = (ASTNode)adaptor.rulePostProcessing(root_0);
			adaptor.setTokenBoundaries(retval.tree, retval.start, retval.stop);
			}
			if ( state.backtracking==0 ) { gParent.popMsg(state); }
		}

		catch (RecognitionException e) {
		  throw e;
		}

		finally {
			// do for sure before leaving
		}
		return retval;
	}
	// $ANTLR end "trfmClause"


	public static class selectExpression_return extends ParserRuleReturnScope {
		ASTNode tree;
		@Override
		public ASTNode getTree() { return tree; }
	};


	// $ANTLR start "selectExpression"
	// SelectClauseParser.g:104:1: selectExpression : ( ( tableAllColumns )=> tableAllColumns | expression );
	public final HiveParser_SelectClauseParser.selectExpression_return selectExpression() throws RecognitionException {
		HiveParser_SelectClauseParser.selectExpression_return retval = new HiveParser_SelectClauseParser.selectExpression_return();
		retval.start = input.LT(1);

		ASTNode root_0 = null;

		ParserRuleReturnScope tableAllColumns45 =null;
		ParserRuleReturnScope expression46 =null;


		 gParent.pushMsg("select expression", state); 
		try {
			// SelectClauseParser.g:107:5: ( ( tableAllColumns )=> tableAllColumns | expression )
			int alt19=2;
			alt19 = dfa19.predict(input);
			switch (alt19) {
				case 1 :
					// SelectClauseParser.g:108:5: ( tableAllColumns )=> tableAllColumns
					{
					root_0 = (ASTNode)adaptor.nil();


					pushFollow(FOLLOW_tableAllColumns_in_selectExpression691);
					tableAllColumns45=gHiveParser.tableAllColumns();
					state._fsp--;
					if (state.failed) return retval;
					if ( state.backtracking==0 ) adaptor.addChild(root_0, tableAllColumns45.getTree());

					}
					break;
				case 2 :
					// SelectClauseParser.g:110:5: expression
					{
					root_0 = (ASTNode)adaptor.nil();


					pushFollow(FOLLOW_expression_in_selectExpression703);
					expression46=gHiveParser.expression();
					state._fsp--;
					if (state.failed) return retval;
					if ( state.backtracking==0 ) adaptor.addChild(root_0, expression46.getTree());

					}
					break;

			}
			retval.stop = input.LT(-1);

			if ( state.backtracking==0 ) {
			retval.tree = (ASTNode)adaptor.rulePostProcessing(root_0);
			adaptor.setTokenBoundaries(retval.tree, retval.start, retval.stop);
			}
			if ( state.backtracking==0 ) { gParent.popMsg(state); }
		}

		catch (RecognitionException e) {
		  throw e;
		}

		finally {
			// do for sure before leaving
		}
		return retval;
	}
	// $ANTLR end "selectExpression"


	public static class selectExpressionList_return extends ParserRuleReturnScope {
		ASTNode tree;
		@Override
		public ASTNode getTree() { return tree; }
	};


	// $ANTLR start "selectExpressionList"
	// SelectClauseParser.g:113:1: selectExpressionList : selectExpression ( COMMA selectExpression )* -> ^( TOK_EXPLIST ( selectExpression )+ ) ;
	public final HiveParser_SelectClauseParser.selectExpressionList_return selectExpressionList() throws RecognitionException {
		HiveParser_SelectClauseParser.selectExpressionList_return retval = new HiveParser_SelectClauseParser.selectExpressionList_return();
		retval.start = input.LT(1);

		ASTNode root_0 = null;

		Token COMMA48=null;
		ParserRuleReturnScope selectExpression47 =null;
		ParserRuleReturnScope selectExpression49 =null;

		ASTNode COMMA48_tree=null;
		RewriteRuleTokenStream stream_COMMA=new RewriteRuleTokenStream(adaptor,"token COMMA");
		RewriteRuleSubtreeStream stream_selectExpression=new RewriteRuleSubtreeStream(adaptor,"rule selectExpression");

		 gParent.pushMsg("select expression list", state); 
		try {
			// SelectClauseParser.g:116:5: ( selectExpression ( COMMA selectExpression )* -> ^( TOK_EXPLIST ( selectExpression )+ ) )
			// SelectClauseParser.g:117:5: selectExpression ( COMMA selectExpression )*
			{
			pushFollow(FOLLOW_selectExpression_in_selectExpressionList734);
			selectExpression47=selectExpression();
			state._fsp--;
			if (state.failed) return retval;
			if ( state.backtracking==0 ) stream_selectExpression.add(selectExpression47.getTree());
			// SelectClauseParser.g:117:22: ( COMMA selectExpression )*
			loop20:
			while (true) {
				int alt20=2;
				int LA20_0 = input.LA(1);
				if ( (LA20_0==COMMA) ) {
					alt20=1;
				}

				switch (alt20) {
				case 1 :
					// SelectClauseParser.g:117:23: COMMA selectExpression
					{
					COMMA48=(Token)match(input,COMMA,FOLLOW_COMMA_in_selectExpressionList737); if (state.failed) return retval; 
					if ( state.backtracking==0 ) stream_COMMA.add(COMMA48);

					pushFollow(FOLLOW_selectExpression_in_selectExpressionList739);
					selectExpression49=selectExpression();
					state._fsp--;
					if (state.failed) return retval;
					if ( state.backtracking==0 ) stream_selectExpression.add(selectExpression49.getTree());
					}
					break;

				default :
					break loop20;
				}
			}

			// AST REWRITE
			// elements: selectExpression
			// token labels: 
			// rule labels: retval
			// token list labels: 
			// rule list labels: 
			// wildcard labels: 
			if ( state.backtracking==0 ) {
			retval.tree = root_0;
			RewriteRuleSubtreeStream stream_retval=new RewriteRuleSubtreeStream(adaptor,"rule retval",retval!=null?retval.getTree():null);

			root_0 = (ASTNode)adaptor.nil();
			// 117:48: -> ^( TOK_EXPLIST ( selectExpression )+ )
			{
				// SelectClauseParser.g:117:51: ^( TOK_EXPLIST ( selectExpression )+ )
				{
				ASTNode root_1 = (ASTNode)adaptor.nil();
				root_1 = (ASTNode)adaptor.becomeRoot((ASTNode)adaptor.create(TOK_EXPLIST, "TOK_EXPLIST"), root_1);
				if ( !(stream_selectExpression.hasNext()) ) {
					throw new RewriteEarlyExitException();
				}
				while ( stream_selectExpression.hasNext() ) {
					adaptor.addChild(root_1, stream_selectExpression.nextTree());
				}
				stream_selectExpression.reset();

				adaptor.addChild(root_0, root_1);
				}

			}


			retval.tree = root_0;
			}

			}

			retval.stop = input.LT(-1);

			if ( state.backtracking==0 ) {
			retval.tree = (ASTNode)adaptor.rulePostProcessing(root_0);
			adaptor.setTokenBoundaries(retval.tree, retval.start, retval.stop);
			}
			if ( state.backtracking==0 ) { gParent.popMsg(state); }
		}

		catch (RecognitionException e) {
		  throw e;
		}

		finally {
			// do for sure before leaving
		}
		return retval;
	}
	// $ANTLR end "selectExpressionList"


	public static class window_clause_return extends ParserRuleReturnScope {
		ASTNode tree;
		@Override
		public ASTNode getTree() { return tree; }
	};


	// $ANTLR start "window_clause"
	// SelectClauseParser.g:121:1: window_clause : KW_WINDOW window_defn ( COMMA window_defn )* -> ^( KW_WINDOW ( window_defn )+ ) ;
	public final HiveParser_SelectClauseParser.window_clause_return window_clause() throws RecognitionException {
		HiveParser_SelectClauseParser.window_clause_return retval = new HiveParser_SelectClauseParser.window_clause_return();
		retval.start = input.LT(1);

		ASTNode root_0 = null;

		Token KW_WINDOW50=null;
		Token COMMA52=null;
		ParserRuleReturnScope window_defn51 =null;
		ParserRuleReturnScope window_defn53 =null;

		ASTNode KW_WINDOW50_tree=null;
		ASTNode COMMA52_tree=null;
		RewriteRuleTokenStream stream_COMMA=new RewriteRuleTokenStream(adaptor,"token COMMA");
		RewriteRuleTokenStream stream_KW_WINDOW=new RewriteRuleTokenStream(adaptor,"token KW_WINDOW");
		RewriteRuleSubtreeStream stream_window_defn=new RewriteRuleSubtreeStream(adaptor,"rule window_defn");

		 gParent.pushMsg("window_clause", state); 
		try {
			// SelectClauseParser.g:124:3: ( KW_WINDOW window_defn ( COMMA window_defn )* -> ^( KW_WINDOW ( window_defn )+ ) )
			// SelectClauseParser.g:125:3: KW_WINDOW window_defn ( COMMA window_defn )*
			{
			KW_WINDOW50=(Token)match(input,KW_WINDOW,FOLLOW_KW_WINDOW_in_window_clause778); if (state.failed) return retval; 
			if ( state.backtracking==0 ) stream_KW_WINDOW.add(KW_WINDOW50);

			pushFollow(FOLLOW_window_defn_in_window_clause780);
			window_defn51=window_defn();
			state._fsp--;
			if (state.failed) return retval;
			if ( state.backtracking==0 ) stream_window_defn.add(window_defn51.getTree());
			// SelectClauseParser.g:125:25: ( COMMA window_defn )*
			loop21:
			while (true) {
				int alt21=2;
				int LA21_0 = input.LA(1);
				if ( (LA21_0==COMMA) ) {
					alt21=1;
				}

				switch (alt21) {
				case 1 :
					// SelectClauseParser.g:125:26: COMMA window_defn
					{
					COMMA52=(Token)match(input,COMMA,FOLLOW_COMMA_in_window_clause783); if (state.failed) return retval; 
					if ( state.backtracking==0 ) stream_COMMA.add(COMMA52);

					pushFollow(FOLLOW_window_defn_in_window_clause785);
					window_defn53=window_defn();
					state._fsp--;
					if (state.failed) return retval;
					if ( state.backtracking==0 ) stream_window_defn.add(window_defn53.getTree());
					}
					break;

				default :
					break loop21;
				}
			}

			// AST REWRITE
			// elements: KW_WINDOW, window_defn
			// token labels: 
			// rule labels: retval
			// token list labels: 
			// rule list labels: 
			// wildcard labels: 
			if ( state.backtracking==0 ) {
			retval.tree = root_0;
			RewriteRuleSubtreeStream stream_retval=new RewriteRuleSubtreeStream(adaptor,"rule retval",retval!=null?retval.getTree():null);

			root_0 = (ASTNode)adaptor.nil();
			// 125:46: -> ^( KW_WINDOW ( window_defn )+ )
			{
				// SelectClauseParser.g:125:49: ^( KW_WINDOW ( window_defn )+ )
				{
				ASTNode root_1 = (ASTNode)adaptor.nil();
				root_1 = (ASTNode)adaptor.becomeRoot(stream_KW_WINDOW.nextNode(), root_1);
				if ( !(stream_window_defn.hasNext()) ) {
					throw new RewriteEarlyExitException();
				}
				while ( stream_window_defn.hasNext() ) {
					adaptor.addChild(root_1, stream_window_defn.nextTree());
				}
				stream_window_defn.reset();

				adaptor.addChild(root_0, root_1);
				}

			}


			retval.tree = root_0;
			}

			}

			retval.stop = input.LT(-1);

			if ( state.backtracking==0 ) {
			retval.tree = (ASTNode)adaptor.rulePostProcessing(root_0);
			adaptor.setTokenBoundaries(retval.tree, retval.start, retval.stop);
			}
			if ( state.backtracking==0 ) { gParent.popMsg(state); }
		}

		catch (RecognitionException e) {
		  throw e;
		}

		finally {
			// do for sure before leaving
		}
		return retval;
	}
	// $ANTLR end "window_clause"


	public static class window_defn_return extends ParserRuleReturnScope {
		ASTNode tree;
		@Override
		public ASTNode getTree() { return tree; }
	};


	// $ANTLR start "window_defn"
	// SelectClauseParser.g:128:1: window_defn : identifier KW_AS window_specification -> ^( TOK_WINDOWDEF identifier window_specification ) ;
	public final HiveParser_SelectClauseParser.window_defn_return window_defn() throws RecognitionException {
		HiveParser_SelectClauseParser.window_defn_return retval = new HiveParser_SelectClauseParser.window_defn_return();
		retval.start = input.LT(1);

		ASTNode root_0 = null;

		Token KW_AS55=null;
		ParserRuleReturnScope identifier54 =null;
		ParserRuleReturnScope window_specification56 =null;

		ASTNode KW_AS55_tree=null;
		RewriteRuleTokenStream stream_KW_AS=new RewriteRuleTokenStream(adaptor,"token KW_AS");
		RewriteRuleSubtreeStream stream_identifier=new RewriteRuleSubtreeStream(adaptor,"rule identifier");
		RewriteRuleSubtreeStream stream_window_specification=new RewriteRuleSubtreeStream(adaptor,"rule window_specification");

		 gParent.pushMsg("window_defn", state); 
		try {
			// SelectClauseParser.g:131:3: ( identifier KW_AS window_specification -> ^( TOK_WINDOWDEF identifier window_specification ) )
			// SelectClauseParser.g:132:3: identifier KW_AS window_specification
			{
			pushFollow(FOLLOW_identifier_in_window_defn821);
			identifier54=gHiveParser.identifier();
			state._fsp--;
			if (state.failed) return retval;
			if ( state.backtracking==0 ) stream_identifier.add(identifier54.getTree());
			KW_AS55=(Token)match(input,KW_AS,FOLLOW_KW_AS_in_window_defn823); if (state.failed) return retval; 
			if ( state.backtracking==0 ) stream_KW_AS.add(KW_AS55);

			pushFollow(FOLLOW_window_specification_in_window_defn825);
			window_specification56=window_specification();
			state._fsp--;
			if (state.failed) return retval;
			if ( state.backtracking==0 ) stream_window_specification.add(window_specification56.getTree());
			// AST REWRITE
			// elements: window_specification, identifier
			// token labels: 
			// rule labels: retval
			// token list labels: 
			// rule list labels: 
			// wildcard labels: 
			if ( state.backtracking==0 ) {
			retval.tree = root_0;
			RewriteRuleSubtreeStream stream_retval=new RewriteRuleSubtreeStream(adaptor,"rule retval",retval!=null?retval.getTree():null);

			root_0 = (ASTNode)adaptor.nil();
			// 132:41: -> ^( TOK_WINDOWDEF identifier window_specification )
			{
				// SelectClauseParser.g:132:44: ^( TOK_WINDOWDEF identifier window_specification )
				{
				ASTNode root_1 = (ASTNode)adaptor.nil();
				root_1 = (ASTNode)adaptor.becomeRoot((ASTNode)adaptor.create(TOK_WINDOWDEF, "TOK_WINDOWDEF"), root_1);
				adaptor.addChild(root_1, stream_identifier.nextTree());
				adaptor.addChild(root_1, stream_window_specification.nextTree());
				adaptor.addChild(root_0, root_1);
				}

			}


			retval.tree = root_0;
			}

			}

			retval.stop = input.LT(-1);

			if ( state.backtracking==0 ) {
			retval.tree = (ASTNode)adaptor.rulePostProcessing(root_0);
			adaptor.setTokenBoundaries(retval.tree, retval.start, retval.stop);
			}
			if ( state.backtracking==0 ) { gParent.popMsg(state); }
		}

		catch (RecognitionException e) {
		  throw e;
		}

		finally {
			// do for sure before leaving
		}
		return retval;
	}
	// $ANTLR end "window_defn"


	public static class window_specification_return extends ParserRuleReturnScope {
		ASTNode tree;
		@Override
		public ASTNode getTree() { return tree; }
	};


	// $ANTLR start "window_specification"
	// SelectClauseParser.g:135:1: window_specification : ( identifier | ( LPAREN ( identifier )? ( partitioningSpec )? ( window_frame )? RPAREN ) ) -> ^( TOK_WINDOWSPEC ( identifier )? ( partitioningSpec )? ( window_frame )? ) ;
	public final HiveParser_SelectClauseParser.window_specification_return window_specification() throws RecognitionException {
		HiveParser_SelectClauseParser.window_specification_return retval = new HiveParser_SelectClauseParser.window_specification_return();
		retval.start = input.LT(1);

		ASTNode root_0 = null;

		Token LPAREN58=null;
		Token RPAREN62=null;
		ParserRuleReturnScope identifier57 =null;
		ParserRuleReturnScope identifier59 =null;
		ParserRuleReturnScope partitioningSpec60 =null;
		ParserRuleReturnScope window_frame61 =null;

		ASTNode LPAREN58_tree=null;
		ASTNode RPAREN62_tree=null;
		RewriteRuleTokenStream stream_LPAREN=new RewriteRuleTokenStream(adaptor,"token LPAREN");
		RewriteRuleTokenStream stream_RPAREN=new RewriteRuleTokenStream(adaptor,"token RPAREN");
		RewriteRuleSubtreeStream stream_identifier=new RewriteRuleSubtreeStream(adaptor,"rule identifier");
		RewriteRuleSubtreeStream stream_partitioningSpec=new RewriteRuleSubtreeStream(adaptor,"rule partitioningSpec");
		RewriteRuleSubtreeStream stream_window_frame=new RewriteRuleSubtreeStream(adaptor,"rule window_frame");

		 gParent.pushMsg("window_specification", state); 
		try {
			// SelectClauseParser.g:138:3: ( ( identifier | ( LPAREN ( identifier )? ( partitioningSpec )? ( window_frame )? RPAREN ) ) -> ^( TOK_WINDOWSPEC ( identifier )? ( partitioningSpec )? ( window_frame )? ) )
			// SelectClauseParser.g:139:3: ( identifier | ( LPAREN ( identifier )? ( partitioningSpec )? ( window_frame )? RPAREN ) )
			{
			// SelectClauseParser.g:139:3: ( identifier | ( LPAREN ( identifier )? ( partitioningSpec )? ( window_frame )? RPAREN ) )
			int alt25=2;
			int LA25_0 = input.LA(1);
			if ( (LA25_0==Identifier||(LA25_0 >= KW_ABORT && LA25_0 <= KW_AFTER)||LA25_0==KW_ANALYZE||LA25_0==KW_ARCHIVE||LA25_0==KW_ASC||(LA25_0 >= KW_AUTOCOMMIT && LA25_0 <= KW_BEFORE)||(LA25_0 >= KW_BUCKET && LA25_0 <= KW_BUCKETS)||(LA25_0 >= KW_CACHE && LA25_0 <= KW_CASCADE)||LA25_0==KW_CHANGE||(LA25_0 >= KW_CLUSTER && LA25_0 <= KW_COLLECTION)||(LA25_0 >= KW_COLUMNS && LA25_0 <= KW_COMMENT)||(LA25_0 >= KW_COMPACT && LA25_0 <= KW_CONCATENATE)||LA25_0==KW_CONTINUE||LA25_0==KW_DATA||LA25_0==KW_DATABASES||(LA25_0 >= KW_DATETIME && LA25_0 <= KW_DBPROPERTIES)||(LA25_0 >= KW_DEFERRED && LA25_0 <= KW_DEFINED)||(LA25_0 >= KW_DELIMITED && LA25_0 <= KW_DESC)||(LA25_0 >= KW_DETAIL && LA25_0 <= KW_DISABLE)||LA25_0==KW_DISTRIBUTE||LA25_0==KW_DOW||(LA25_0 >= KW_DUMP && LA25_0 <= KW_ELEM_TYPE)||LA25_0==KW_ENABLE||LA25_0==KW_ESCAPED||LA25_0==KW_EXCLUSIVE||(LA25_0 >= KW_EXPLAIN && LA25_0 <= KW_EXPRESSION)||(LA25_0 >= KW_FIELDS && LA25_0 <= KW_FIRST)||(LA25_0 >= KW_FORMAT && LA25_0 <= KW_FORMATTED)||LA25_0==KW_FUNCTIONS||(LA25_0 >= KW_HOUR && LA25_0 <= KW_IDXPROPERTIES)||(LA25_0 >= KW_INDEX && LA25_0 <= KW_INDEXES)||(LA25_0 >= KW_INPATH && LA25_0 <= KW_INPUTFORMAT)||(LA25_0 >= KW_ISOLATION && LA25_0 <= KW_JAR)||(LA25_0 >= KW_KEY && LA25_0 <= KW_LAST)||LA25_0==KW_LEVEL||(LA25_0 >= KW_LIMIT && LA25_0 <= KW_LOAD)||(LA25_0 >= KW_LOCATION && LA25_0 <= KW_LONG)||(LA25_0 >= KW_MAPJOIN && LA25_0 <= KW_MATERIALIZED)||LA25_0==KW_METADATA||(LA25_0 >= KW_MINUTE && LA25_0 <= KW_MONTH)||LA25_0==KW_MSCK||(LA25_0 >= KW_NORELY && LA25_0 <= KW_NOSCAN)||LA25_0==KW_NOVALIDATE||LA25_0==KW_NULLS||LA25_0==KW_OFFSET||(LA25_0 >= KW_OPERATOR && LA25_0 <= KW_OPTION)||(LA25_0 >= KW_OUTPUTDRIVER && LA25_0 <= KW_OUTPUTFORMAT)||(LA25_0 >= KW_OVERWRITE && LA25_0 <= KW_OWNER)||(LA25_0 >= KW_PARTITIONED && LA25_0 <= KW_PARTITIONS)||LA25_0==KW_PLUS||LA25_0==KW_PRETTY||LA25_0==KW_PRINCIPALS||(LA25_0 >= KW_PURGE && LA25_0 <= KW_QUARTER)||LA25_0==KW_READ||(LA25_0 >= KW_REBUILD && LA25_0 <= KW_RECORDWRITER)||(LA25_0 >= KW_RELOAD && LA25_0 <= KW_RESTRICT)||LA25_0==KW_REWRITE||(LA25_0 >= KW_ROLE && LA25_0 <= KW_ROLES)||(LA25_0 >= KW_SCHEMA && LA25_0 <= KW_SECOND)||(LA25_0 >= KW_SEMI && LA25_0 <= KW_SERVER)||(LA25_0 >= KW_SETS && LA25_0 <= KW_SKEWED)||(LA25_0 >= KW_SNAPSHOT && LA25_0 <= KW_SSL)||(LA25_0 >= KW_STATISTICS && LA25_0 <= KW_SUMMARY)||LA25_0==KW_TABLES||(LA25_0 >= KW_TBLPROPERTIES && LA25_0 <= KW_TERMINATED)||LA25_0==KW_TINYINT||(LA25_0 >= KW_TOUCH && LA25_0 <= KW_TRANSACTIONS)||LA25_0==KW_UNARCHIVE||LA25_0==KW_UNDO||LA25_0==KW_UNIONTYPE||(LA25_0 >= KW_UNLOCK && LA25_0 <= KW_UNSIGNED)||(LA25_0 >= KW_URI && LA25_0 <= KW_USE)||(LA25_0 >= KW_UTC && LA25_0 <= KW_VALIDATE)||LA25_0==KW_VALUE_TYPE||(LA25_0 >= KW_VECTORIZATION && LA25_0 <= KW_WEEK)||LA25_0==KW_WHILE||(LA25_0 >= KW_WORK && LA25_0 <= KW_YEAR)||LA25_0==KW_BATCH||LA25_0==KW_DAYOFWEEK||LA25_0==KW_HOLD_DDLTIME||LA25_0==KW_IGNORE||LA25_0==KW_NO_DROP||LA25_0==KW_OFFLINE||LA25_0==KW_PROTECTION||LA25_0==KW_READONLY) ) {
				alt25=1;
			}
			else if ( (LA25_0==LPAREN) ) {
				alt25=2;
			}

			else {
				if (state.backtracking>0) {state.failed=true; return retval;}
				NoViableAltException nvae =
					new NoViableAltException("", 25, 0, input);
				throw nvae;
			}

			switch (alt25) {
				case 1 :
					// SelectClauseParser.g:139:4: identifier
					{
					pushFollow(FOLLOW_identifier_in_window_specification861);
					identifier57=gHiveParser.identifier();
					state._fsp--;
					if (state.failed) return retval;
					if ( state.backtracking==0 ) stream_identifier.add(identifier57.getTree());
					}
					break;
				case 2 :
					// SelectClauseParser.g:139:17: ( LPAREN ( identifier )? ( partitioningSpec )? ( window_frame )? RPAREN )
					{
					// SelectClauseParser.g:139:17: ( LPAREN ( identifier )? ( partitioningSpec )? ( window_frame )? RPAREN )
					// SelectClauseParser.g:139:19: LPAREN ( identifier )? ( partitioningSpec )? ( window_frame )? RPAREN
					{
					LPAREN58=(Token)match(input,LPAREN,FOLLOW_LPAREN_in_window_specification867); if (state.failed) return retval; 
					if ( state.backtracking==0 ) stream_LPAREN.add(LPAREN58);

					// SelectClauseParser.g:139:26: ( identifier )?
					int alt22=2;
					switch ( input.LA(1) ) {
						case Identifier:
						case KW_ABORT:
						case KW_ADD:
						case KW_ADMIN:
						case KW_AFTER:
						case KW_ANALYZE:
						case KW_ARCHIVE:
						case KW_ASC:
						case KW_AUTOCOMMIT:
						case KW_BEFORE:
						case KW_BUCKET:
						case KW_BUCKETS:
						case KW_CACHE:
						case KW_CASCADE:
						case KW_CHANGE:
						case KW_CLUSTERED:
						case KW_CLUSTERSTATUS:
						case KW_COLLECTION:
						case KW_COLUMNS:
						case KW_COMMENT:
						case KW_COMPACT:
						case KW_COMPACTIONS:
						case KW_COMPUTE:
						case KW_CONCATENATE:
						case KW_CONTINUE:
						case KW_DATA:
						case KW_DATABASES:
						case KW_DATETIME:
						case KW_DAY:
						case KW_DBPROPERTIES:
						case KW_DEFERRED:
						case KW_DEFINED:
						case KW_DELIMITED:
						case KW_DEPENDENCY:
						case KW_DESC:
						case KW_DETAIL:
						case KW_DIRECTORIES:
						case KW_DIRECTORY:
						case KW_DISABLE:
						case KW_DOW:
						case KW_DUMP:
						case KW_ELEM_TYPE:
						case KW_ENABLE:
						case KW_ESCAPED:
						case KW_EXCLUSIVE:
						case KW_EXPLAIN:
						case KW_EXPORT:
						case KW_EXPRESSION:
						case KW_FIELDS:
						case KW_FILE:
						case KW_FILEFORMAT:
						case KW_FIRST:
						case KW_FORMAT:
						case KW_FORMATTED:
						case KW_FUNCTIONS:
						case KW_HOUR:
						case KW_IDXPROPERTIES:
						case KW_INDEX:
						case KW_INDEXES:
						case KW_INPATH:
						case KW_INPUTDRIVER:
						case KW_INPUTFORMAT:
						case KW_ISOLATION:
						case KW_ITEMS:
						case KW_JAR:
						case KW_KEY:
						case KW_KEYS:
						case KW_KEY_TYPE:
						case KW_LAST:
						case KW_LEVEL:
						case KW_LIMIT:
						case KW_LINES:
						case KW_LOAD:
						case KW_LOCATION:
						case KW_LOCK:
						case KW_LOCKS:
						case KW_LOGICAL:
						case KW_LONG:
						case KW_MAPJOIN:
						case KW_MATCHED:
						case KW_MATERIALIZED:
						case KW_METADATA:
						case KW_MINUTE:
						case KW_MONTH:
						case KW_MSCK:
						case KW_NORELY:
						case KW_NOSCAN:
						case KW_NOVALIDATE:
						case KW_NULLS:
						case KW_OFFSET:
						case KW_OPERATOR:
						case KW_OPTION:
						case KW_OUTPUTDRIVER:
						case KW_OUTPUTFORMAT:
						case KW_OVERWRITE:
						case KW_OWNER:
						case KW_PARTITIONED:
						case KW_PARTITIONS:
						case KW_PLUS:
						case KW_PRETTY:
						case KW_PRINCIPALS:
						case KW_PURGE:
						case KW_QUARTER:
						case KW_READ:
						case KW_REBUILD:
						case KW_RECORDREADER:
						case KW_RECORDWRITER:
						case KW_RELOAD:
						case KW_RELY:
						case KW_RENAME:
						case KW_REPAIR:
						case KW_REPL:
						case KW_REPLACE:
						case KW_REPLICATION:
						case KW_RESTRICT:
						case KW_REWRITE:
						case KW_ROLE:
						case KW_ROLES:
						case KW_SCHEMA:
						case KW_SCHEMAS:
						case KW_SECOND:
						case KW_SEMI:
						case KW_SERDE:
						case KW_SERDEPROPERTIES:
						case KW_SERVER:
						case KW_SETS:
						case KW_SHARED:
						case KW_SHOW:
						case KW_SHOW_DATABASE:
						case KW_SKEWED:
						case KW_SNAPSHOT:
						case KW_SORTED:
						case KW_SSL:
						case KW_STATISTICS:
						case KW_STATUS:
						case KW_STORED:
						case KW_STREAMTABLE:
						case KW_STRING:
						case KW_STRUCT:
						case KW_SUMMARY:
						case KW_TABLES:
						case KW_TBLPROPERTIES:
						case KW_TEMPORARY:
						case KW_TERMINATED:
						case KW_TINYINT:
						case KW_TOUCH:
						case KW_TRANSACTION:
						case KW_TRANSACTIONS:
						case KW_UNARCHIVE:
						case KW_UNDO:
						case KW_UNIONTYPE:
						case KW_UNLOCK:
						case KW_UNSET:
						case KW_UNSIGNED:
						case KW_URI:
						case KW_USE:
						case KW_UTC:
						case KW_UTCTIMESTAMP:
						case KW_VALIDATE:
						case KW_VALUE_TYPE:
						case KW_VECTORIZATION:
						case KW_VIEW:
						case KW_VIEWS:
						case KW_WAIT:
						case KW_WEEK:
						case KW_WHILE:
						case KW_WORK:
						case KW_WRITE:
						case KW_YEAR:
						case KW_BATCH:
						case KW_DAYOFWEEK:
						case KW_HOLD_DDLTIME:
						case KW_IGNORE:
						case KW_NO_DROP:
						case KW_OFFLINE:
						case KW_PROTECTION:
						case KW_READONLY:
							{
							alt22=1;
							}
							break;
						case KW_DISTRIBUTE:
							{
							int LA22_2 = input.LA(2);
							if ( (LA22_2==KW_CLUSTER||LA22_2==KW_DISTRIBUTE||LA22_2==KW_ORDER||LA22_2==KW_PARTITION||LA22_2==KW_RANGE||LA22_2==KW_ROWS||LA22_2==KW_SORT||LA22_2==RPAREN) ) {
								alt22=1;
							}
							}
							break;
						case KW_SORT:
							{
							int LA22_5 = input.LA(2);
							if ( (LA22_5==KW_CLUSTER||LA22_5==KW_DISTRIBUTE||LA22_5==KW_ORDER||LA22_5==KW_PARTITION||LA22_5==KW_RANGE||LA22_5==KW_ROWS||LA22_5==KW_SORT||LA22_5==RPAREN) ) {
								alt22=1;
							}
							}
							break;
						case KW_CLUSTER:
							{
							int LA22_6 = input.LA(2);
							if ( (LA22_6==KW_CLUSTER||LA22_6==KW_DISTRIBUTE||LA22_6==KW_ORDER||LA22_6==KW_PARTITION||LA22_6==KW_RANGE||LA22_6==KW_ROWS||LA22_6==KW_SORT||LA22_6==RPAREN) ) {
								alt22=1;
							}
							}
							break;
					}
					switch (alt22) {
						case 1 :
							// SelectClauseParser.g:139:26: identifier
							{
							pushFollow(FOLLOW_identifier_in_window_specification869);
							identifier59=gHiveParser.identifier();
							state._fsp--;
							if (state.failed) return retval;
							if ( state.backtracking==0 ) stream_identifier.add(identifier59.getTree());
							}
							break;

					}

					// SelectClauseParser.g:139:38: ( partitioningSpec )?
					int alt23=2;
					int LA23_0 = input.LA(1);
					if ( (LA23_0==KW_CLUSTER||LA23_0==KW_DISTRIBUTE||LA23_0==KW_ORDER||LA23_0==KW_PARTITION||LA23_0==KW_SORT) ) {
						alt23=1;
					}
					switch (alt23) {
						case 1 :
							// SelectClauseParser.g:139:38: partitioningSpec
							{
							pushFollow(FOLLOW_partitioningSpec_in_window_specification872);
							partitioningSpec60=gHiveParser.partitioningSpec();
							state._fsp--;
							if (state.failed) return retval;
							if ( state.backtracking==0 ) stream_partitioningSpec.add(partitioningSpec60.getTree());
							}
							break;

					}

					// SelectClauseParser.g:139:56: ( window_frame )?
					int alt24=2;
					int LA24_0 = input.LA(1);
					if ( (LA24_0==KW_RANGE||LA24_0==KW_ROWS) ) {
						alt24=1;
					}
					switch (alt24) {
						case 1 :
							// SelectClauseParser.g:139:56: window_frame
							{
							pushFollow(FOLLOW_window_frame_in_window_specification875);
							window_frame61=window_frame();
							state._fsp--;
							if (state.failed) return retval;
							if ( state.backtracking==0 ) stream_window_frame.add(window_frame61.getTree());
							}
							break;

					}

					RPAREN62=(Token)match(input,RPAREN,FOLLOW_RPAREN_in_window_specification878); if (state.failed) return retval; 
					if ( state.backtracking==0 ) stream_RPAREN.add(RPAREN62);

					}

					}
					break;

			}

			// AST REWRITE
			// elements: identifier, window_frame, partitioningSpec
			// token labels: 
			// rule labels: retval
			// token list labels: 
			// rule list labels: 
			// wildcard labels: 
			if ( state.backtracking==0 ) {
			retval.tree = root_0;
			RewriteRuleSubtreeStream stream_retval=new RewriteRuleSubtreeStream(adaptor,"rule retval",retval!=null?retval.getTree():null);

			root_0 = (ASTNode)adaptor.nil();
			// 139:79: -> ^( TOK_WINDOWSPEC ( identifier )? ( partitioningSpec )? ( window_frame )? )
			{
				// SelectClauseParser.g:139:82: ^( TOK_WINDOWSPEC ( identifier )? ( partitioningSpec )? ( window_frame )? )
				{
				ASTNode root_1 = (ASTNode)adaptor.nil();
				root_1 = (ASTNode)adaptor.becomeRoot((ASTNode)adaptor.create(TOK_WINDOWSPEC, "TOK_WINDOWSPEC"), root_1);
				// SelectClauseParser.g:139:99: ( identifier )?
				if ( stream_identifier.hasNext() ) {
					adaptor.addChild(root_1, stream_identifier.nextTree());
				}
				stream_identifier.reset();

				// SelectClauseParser.g:139:111: ( partitioningSpec )?
				if ( stream_partitioningSpec.hasNext() ) {
					adaptor.addChild(root_1, stream_partitioningSpec.nextTree());
				}
				stream_partitioningSpec.reset();

				// SelectClauseParser.g:139:129: ( window_frame )?
				if ( stream_window_frame.hasNext() ) {
					adaptor.addChild(root_1, stream_window_frame.nextTree());
				}
				stream_window_frame.reset();

				adaptor.addChild(root_0, root_1);
				}

			}


			retval.tree = root_0;
			}

			}

			retval.stop = input.LT(-1);

			if ( state.backtracking==0 ) {
			retval.tree = (ASTNode)adaptor.rulePostProcessing(root_0);
			adaptor.setTokenBoundaries(retval.tree, retval.start, retval.stop);
			}
			if ( state.backtracking==0 ) { gParent.popMsg(state); }
		}

		catch (RecognitionException e) {
		  throw e;
		}

		finally {
			// do for sure before leaving
		}
		return retval;
	}
	// $ANTLR end "window_specification"


	public static class window_frame_return extends ParserRuleReturnScope {
		ASTNode tree;
		@Override
		public ASTNode getTree() { return tree; }
	};


	// $ANTLR start "window_frame"
	// SelectClauseParser.g:142:1: window_frame : ( window_range_expression | window_value_expression );
	public final HiveParser_SelectClauseParser.window_frame_return window_frame() throws RecognitionException {
		HiveParser_SelectClauseParser.window_frame_return retval = new HiveParser_SelectClauseParser.window_frame_return();
		retval.start = input.LT(1);

		ASTNode root_0 = null;

		ParserRuleReturnScope window_range_expression63 =null;
		ParserRuleReturnScope window_value_expression64 =null;


		try {
			// SelectClauseParser.g:142:14: ( window_range_expression | window_value_expression )
			int alt26=2;
			int LA26_0 = input.LA(1);
			if ( (LA26_0==KW_ROWS) ) {
				alt26=1;
			}
			else if ( (LA26_0==KW_RANGE) ) {
				alt26=2;
			}

			else {
				if (state.backtracking>0) {state.failed=true; return retval;}
				NoViableAltException nvae =
					new NoViableAltException("", 26, 0, input);
				throw nvae;
			}

			switch (alt26) {
				case 1 :
					// SelectClauseParser.g:143:2: window_range_expression
					{
					root_0 = (ASTNode)adaptor.nil();


					pushFollow(FOLLOW_window_range_expression_in_window_frame905);
					window_range_expression63=window_range_expression();
					state._fsp--;
					if (state.failed) return retval;
					if ( state.backtracking==0 ) adaptor.addChild(root_0, window_range_expression63.getTree());

					}
					break;
				case 2 :
					// SelectClauseParser.g:144:2: window_value_expression
					{
					root_0 = (ASTNode)adaptor.nil();


					pushFollow(FOLLOW_window_value_expression_in_window_frame910);
					window_value_expression64=window_value_expression();
					state._fsp--;
					if (state.failed) return retval;
					if ( state.backtracking==0 ) adaptor.addChild(root_0, window_value_expression64.getTree());

					}
					break;

			}
			retval.stop = input.LT(-1);

			if ( state.backtracking==0 ) {
			retval.tree = (ASTNode)adaptor.rulePostProcessing(root_0);
			adaptor.setTokenBoundaries(retval.tree, retval.start, retval.stop);
			}
		}

		catch (RecognitionException e) {
		  throw e;
		}

		finally {
			// do for sure before leaving
		}
		return retval;
	}
	// $ANTLR end "window_frame"


	public static class window_range_expression_return extends ParserRuleReturnScope {
		ASTNode tree;
		@Override
		public ASTNode getTree() { return tree; }
	};


	// $ANTLR start "window_range_expression"
	// SelectClauseParser.g:147:1: window_range_expression : ( KW_ROWS sb= window_frame_start_boundary -> ^( TOK_WINDOWRANGE $sb) | KW_ROWS KW_BETWEEN s= window_frame_boundary KW_AND end= window_frame_boundary -> ^( TOK_WINDOWRANGE $s $end) );
	public final HiveParser_SelectClauseParser.window_range_expression_return window_range_expression() throws RecognitionException {
		HiveParser_SelectClauseParser.window_range_expression_return retval = new HiveParser_SelectClauseParser.window_range_expression_return();
		retval.start = input.LT(1);

		ASTNode root_0 = null;

		Token KW_ROWS65=null;
		Token KW_ROWS66=null;
		Token KW_BETWEEN67=null;
		Token KW_AND68=null;
		ParserRuleReturnScope sb =null;
		ParserRuleReturnScope s =null;
		ParserRuleReturnScope end =null;

		ASTNode KW_ROWS65_tree=null;
		ASTNode KW_ROWS66_tree=null;
		ASTNode KW_BETWEEN67_tree=null;
		ASTNode KW_AND68_tree=null;
		RewriteRuleTokenStream stream_KW_BETWEEN=new RewriteRuleTokenStream(adaptor,"token KW_BETWEEN");
		RewriteRuleTokenStream stream_KW_AND=new RewriteRuleTokenStream(adaptor,"token KW_AND");
		RewriteRuleTokenStream stream_KW_ROWS=new RewriteRuleTokenStream(adaptor,"token KW_ROWS");
		RewriteRuleSubtreeStream stream_window_frame_start_boundary=new RewriteRuleSubtreeStream(adaptor,"rule window_frame_start_boundary");
		RewriteRuleSubtreeStream stream_window_frame_boundary=new RewriteRuleSubtreeStream(adaptor,"rule window_frame_boundary");

		 gParent.pushMsg("window_range_expression", state); 
		try {
			// SelectClauseParser.g:150:2: ( KW_ROWS sb= window_frame_start_boundary -> ^( TOK_WINDOWRANGE $sb) | KW_ROWS KW_BETWEEN s= window_frame_boundary KW_AND end= window_frame_boundary -> ^( TOK_WINDOWRANGE $s $end) )
			int alt27=2;
			int LA27_0 = input.LA(1);
			if ( (LA27_0==KW_ROWS) ) {
				int LA27_1 = input.LA(2);
				if ( (LA27_1==KW_BETWEEN) ) {
					alt27=2;
				}
				else if ( (LA27_1==KW_CURRENT||LA27_1==KW_UNBOUNDED||LA27_1==Number) ) {
					alt27=1;
				}

				else {
					if (state.backtracking>0) {state.failed=true; return retval;}
					int nvaeMark = input.mark();
					try {
						input.consume();
						NoViableAltException nvae =
							new NoViableAltException("", 27, 1, input);
						throw nvae;
					} finally {
						input.rewind(nvaeMark);
					}
				}

			}

			else {
				if (state.backtracking>0) {state.failed=true; return retval;}
				NoViableAltException nvae =
					new NoViableAltException("", 27, 0, input);
				throw nvae;
			}

			switch (alt27) {
				case 1 :
					// SelectClauseParser.g:151:2: KW_ROWS sb= window_frame_start_boundary
					{
					KW_ROWS65=(Token)match(input,KW_ROWS,FOLLOW_KW_ROWS_in_window_range_expression932); if (state.failed) return retval; 
					if ( state.backtracking==0 ) stream_KW_ROWS.add(KW_ROWS65);

					pushFollow(FOLLOW_window_frame_start_boundary_in_window_range_expression936);
					sb=window_frame_start_boundary();
					state._fsp--;
					if (state.failed) return retval;
					if ( state.backtracking==0 ) stream_window_frame_start_boundary.add(sb.getTree());
					// AST REWRITE
					// elements: sb
					// token labels: 
					// rule labels: retval, sb
					// token list labels: 
					// rule list labels: 
					// wildcard labels: 
					if ( state.backtracking==0 ) {
					retval.tree = root_0;
					RewriteRuleSubtreeStream stream_retval=new RewriteRuleSubtreeStream(adaptor,"rule retval",retval!=null?retval.getTree():null);
					RewriteRuleSubtreeStream stream_sb=new RewriteRuleSubtreeStream(adaptor,"rule sb",sb!=null?sb.getTree():null);

					root_0 = (ASTNode)adaptor.nil();
					// 151:41: -> ^( TOK_WINDOWRANGE $sb)
					{
						// SelectClauseParser.g:151:44: ^( TOK_WINDOWRANGE $sb)
						{
						ASTNode root_1 = (ASTNode)adaptor.nil();
						root_1 = (ASTNode)adaptor.becomeRoot((ASTNode)adaptor.create(TOK_WINDOWRANGE, "TOK_WINDOWRANGE"), root_1);
						adaptor.addChild(root_1, stream_sb.nextTree());
						adaptor.addChild(root_0, root_1);
						}

					}


					retval.tree = root_0;
					}

					}
					break;
				case 2 :
					// SelectClauseParser.g:152:2: KW_ROWS KW_BETWEEN s= window_frame_boundary KW_AND end= window_frame_boundary
					{
					KW_ROWS66=(Token)match(input,KW_ROWS,FOLLOW_KW_ROWS_in_window_range_expression950); if (state.failed) return retval; 
					if ( state.backtracking==0 ) stream_KW_ROWS.add(KW_ROWS66);

					KW_BETWEEN67=(Token)match(input,KW_BETWEEN,FOLLOW_KW_BETWEEN_in_window_range_expression952); if (state.failed) return retval; 
					if ( state.backtracking==0 ) stream_KW_BETWEEN.add(KW_BETWEEN67);

					pushFollow(FOLLOW_window_frame_boundary_in_window_range_expression956);
					s=window_frame_boundary();
					state._fsp--;
					if (state.failed) return retval;
					if ( state.backtracking==0 ) stream_window_frame_boundary.add(s.getTree());
					KW_AND68=(Token)match(input,KW_AND,FOLLOW_KW_AND_in_window_range_expression958); if (state.failed) return retval; 
					if ( state.backtracking==0 ) stream_KW_AND.add(KW_AND68);

					pushFollow(FOLLOW_window_frame_boundary_in_window_range_expression962);
					end=window_frame_boundary();
					state._fsp--;
					if (state.failed) return retval;
					if ( state.backtracking==0 ) stream_window_frame_boundary.add(end.getTree());
					// AST REWRITE
					// elements: end, s
					// token labels: 
					// rule labels: s, end, retval
					// token list labels: 
					// rule list labels: 
					// wildcard labels: 
					if ( state.backtracking==0 ) {
					retval.tree = root_0;
					RewriteRuleSubtreeStream stream_s=new RewriteRuleSubtreeStream(adaptor,"rule s",s!=null?s.getTree():null);
					RewriteRuleSubtreeStream stream_end=new RewriteRuleSubtreeStream(adaptor,"rule end",end!=null?end.getTree():null);
					RewriteRuleSubtreeStream stream_retval=new RewriteRuleSubtreeStream(adaptor,"rule retval",retval!=null?retval.getTree():null);

					root_0 = (ASTNode)adaptor.nil();
					// 152:78: -> ^( TOK_WINDOWRANGE $s $end)
					{
						// SelectClauseParser.g:152:81: ^( TOK_WINDOWRANGE $s $end)
						{
						ASTNode root_1 = (ASTNode)adaptor.nil();
						root_1 = (ASTNode)adaptor.becomeRoot((ASTNode)adaptor.create(TOK_WINDOWRANGE, "TOK_WINDOWRANGE"), root_1);
						adaptor.addChild(root_1, stream_s.nextTree());
						adaptor.addChild(root_1, stream_end.nextTree());
						adaptor.addChild(root_0, root_1);
						}

					}


					retval.tree = root_0;
					}

					}
					break;

			}
			retval.stop = input.LT(-1);

			if ( state.backtracking==0 ) {
			retval.tree = (ASTNode)adaptor.rulePostProcessing(root_0);
			adaptor.setTokenBoundaries(retval.tree, retval.start, retval.stop);
			}
			if ( state.backtracking==0 ) { gParent.popMsg(state); }
		}

		catch (RecognitionException e) {
		  throw e;
		}

		finally {
			// do for sure before leaving
		}
		return retval;
	}
	// $ANTLR end "window_range_expression"


	public static class window_value_expression_return extends ParserRuleReturnScope {
		ASTNode tree;
		@Override
		public ASTNode getTree() { return tree; }
	};


	// $ANTLR start "window_value_expression"
	// SelectClauseParser.g:155:1: window_value_expression : ( KW_RANGE sb= window_frame_start_boundary -> ^( TOK_WINDOWVALUES $sb) | KW_RANGE KW_BETWEEN s= window_frame_boundary KW_AND end= window_frame_boundary -> ^( TOK_WINDOWVALUES $s $end) );
	public final HiveParser_SelectClauseParser.window_value_expression_return window_value_expression() throws RecognitionException {
		HiveParser_SelectClauseParser.window_value_expression_return retval = new HiveParser_SelectClauseParser.window_value_expression_return();
		retval.start = input.LT(1);

		ASTNode root_0 = null;

		Token KW_RANGE69=null;
		Token KW_RANGE70=null;
		Token KW_BETWEEN71=null;
		Token KW_AND72=null;
		ParserRuleReturnScope sb =null;
		ParserRuleReturnScope s =null;
		ParserRuleReturnScope end =null;

		ASTNode KW_RANGE69_tree=null;
		ASTNode KW_RANGE70_tree=null;
		ASTNode KW_BETWEEN71_tree=null;
		ASTNode KW_AND72_tree=null;
		RewriteRuleTokenStream stream_KW_BETWEEN=new RewriteRuleTokenStream(adaptor,"token KW_BETWEEN");
		RewriteRuleTokenStream stream_KW_AND=new RewriteRuleTokenStream(adaptor,"token KW_AND");
		RewriteRuleTokenStream stream_KW_RANGE=new RewriteRuleTokenStream(adaptor,"token KW_RANGE");
		RewriteRuleSubtreeStream stream_window_frame_start_boundary=new RewriteRuleSubtreeStream(adaptor,"rule window_frame_start_boundary");
		RewriteRuleSubtreeStream stream_window_frame_boundary=new RewriteRuleSubtreeStream(adaptor,"rule window_frame_boundary");

		 gParent.pushMsg("window_value_expression", state); 
		try {
			// SelectClauseParser.g:158:2: ( KW_RANGE sb= window_frame_start_boundary -> ^( TOK_WINDOWVALUES $sb) | KW_RANGE KW_BETWEEN s= window_frame_boundary KW_AND end= window_frame_boundary -> ^( TOK_WINDOWVALUES $s $end) )
			int alt28=2;
			int LA28_0 = input.LA(1);
			if ( (LA28_0==KW_RANGE) ) {
				int LA28_1 = input.LA(2);
				if ( (LA28_1==KW_BETWEEN) ) {
					alt28=2;
				}
				else if ( (LA28_1==KW_CURRENT||LA28_1==KW_UNBOUNDED||LA28_1==Number) ) {
					alt28=1;
				}

				else {
					if (state.backtracking>0) {state.failed=true; return retval;}
					int nvaeMark = input.mark();
					try {
						input.consume();
						NoViableAltException nvae =
							new NoViableAltException("", 28, 1, input);
						throw nvae;
					} finally {
						input.rewind(nvaeMark);
					}
				}

			}

			else {
				if (state.backtracking>0) {state.failed=true; return retval;}
				NoViableAltException nvae =
					new NoViableAltException("", 28, 0, input);
				throw nvae;
			}

			switch (alt28) {
				case 1 :
					// SelectClauseParser.g:159:2: KW_RANGE sb= window_frame_start_boundary
					{
					KW_RANGE69=(Token)match(input,KW_RANGE,FOLLOW_KW_RANGE_in_window_value_expression996); if (state.failed) return retval; 
					if ( state.backtracking==0 ) stream_KW_RANGE.add(KW_RANGE69);

					pushFollow(FOLLOW_window_frame_start_boundary_in_window_value_expression1000);
					sb=window_frame_start_boundary();
					state._fsp--;
					if (state.failed) return retval;
					if ( state.backtracking==0 ) stream_window_frame_start_boundary.add(sb.getTree());
					// AST REWRITE
					// elements: sb
					// token labels: 
					// rule labels: retval, sb
					// token list labels: 
					// rule list labels: 
					// wildcard labels: 
					if ( state.backtracking==0 ) {
					retval.tree = root_0;
					RewriteRuleSubtreeStream stream_retval=new RewriteRuleSubtreeStream(adaptor,"rule retval",retval!=null?retval.getTree():null);
					RewriteRuleSubtreeStream stream_sb=new RewriteRuleSubtreeStream(adaptor,"rule sb",sb!=null?sb.getTree():null);

					root_0 = (ASTNode)adaptor.nil();
					// 159:42: -> ^( TOK_WINDOWVALUES $sb)
					{
						// SelectClauseParser.g:159:45: ^( TOK_WINDOWVALUES $sb)
						{
						ASTNode root_1 = (ASTNode)adaptor.nil();
						root_1 = (ASTNode)adaptor.becomeRoot((ASTNode)adaptor.create(TOK_WINDOWVALUES, "TOK_WINDOWVALUES"), root_1);
						adaptor.addChild(root_1, stream_sb.nextTree());
						adaptor.addChild(root_0, root_1);
						}

					}


					retval.tree = root_0;
					}

					}
					break;
				case 2 :
					// SelectClauseParser.g:160:2: KW_RANGE KW_BETWEEN s= window_frame_boundary KW_AND end= window_frame_boundary
					{
					KW_RANGE70=(Token)match(input,KW_RANGE,FOLLOW_KW_RANGE_in_window_value_expression1014); if (state.failed) return retval; 
					if ( state.backtracking==0 ) stream_KW_RANGE.add(KW_RANGE70);

					KW_BETWEEN71=(Token)match(input,KW_BETWEEN,FOLLOW_KW_BETWEEN_in_window_value_expression1016); if (state.failed) return retval; 
					if ( state.backtracking==0 ) stream_KW_BETWEEN.add(KW_BETWEEN71);

					pushFollow(FOLLOW_window_frame_boundary_in_window_value_expression1020);
					s=window_frame_boundary();
					state._fsp--;
					if (state.failed) return retval;
					if ( state.backtracking==0 ) stream_window_frame_boundary.add(s.getTree());
					KW_AND72=(Token)match(input,KW_AND,FOLLOW_KW_AND_in_window_value_expression1022); if (state.failed) return retval; 
					if ( state.backtracking==0 ) stream_KW_AND.add(KW_AND72);

					pushFollow(FOLLOW_window_frame_boundary_in_window_value_expression1026);
					end=window_frame_boundary();
					state._fsp--;
					if (state.failed) return retval;
					if ( state.backtracking==0 ) stream_window_frame_boundary.add(end.getTree());
					// AST REWRITE
					// elements: s, end
					// token labels: 
					// rule labels: s, end, retval
					// token list labels: 
					// rule list labels: 
					// wildcard labels: 
					if ( state.backtracking==0 ) {
					retval.tree = root_0;
					RewriteRuleSubtreeStream stream_s=new RewriteRuleSubtreeStream(adaptor,"rule s",s!=null?s.getTree():null);
					RewriteRuleSubtreeStream stream_end=new RewriteRuleSubtreeStream(adaptor,"rule end",end!=null?end.getTree():null);
					RewriteRuleSubtreeStream stream_retval=new RewriteRuleSubtreeStream(adaptor,"rule retval",retval!=null?retval.getTree():null);

					root_0 = (ASTNode)adaptor.nil();
					// 160:79: -> ^( TOK_WINDOWVALUES $s $end)
					{
						// SelectClauseParser.g:160:82: ^( TOK_WINDOWVALUES $s $end)
						{
						ASTNode root_1 = (ASTNode)adaptor.nil();
						root_1 = (ASTNode)adaptor.becomeRoot((ASTNode)adaptor.create(TOK_WINDOWVALUES, "TOK_WINDOWVALUES"), root_1);
						adaptor.addChild(root_1, stream_s.nextTree());
						adaptor.addChild(root_1, stream_end.nextTree());
						adaptor.addChild(root_0, root_1);
						}

					}


					retval.tree = root_0;
					}

					}
					break;

			}
			retval.stop = input.LT(-1);

			if ( state.backtracking==0 ) {
			retval.tree = (ASTNode)adaptor.rulePostProcessing(root_0);
			adaptor.setTokenBoundaries(retval.tree, retval.start, retval.stop);
			}
			if ( state.backtracking==0 ) { gParent.popMsg(state); }
		}

		catch (RecognitionException e) {
		  throw e;
		}

		finally {
			// do for sure before leaving
		}
		return retval;
	}
	// $ANTLR end "window_value_expression"


	public static class window_frame_start_boundary_return extends ParserRuleReturnScope {
		ASTNode tree;
		@Override
		public ASTNode getTree() { return tree; }
	};


	// $ANTLR start "window_frame_start_boundary"
	// SelectClauseParser.g:163:1: window_frame_start_boundary : ( KW_UNBOUNDED KW_PRECEDING -> ^( KW_PRECEDING KW_UNBOUNDED ) | KW_CURRENT KW_ROW -> ^( KW_CURRENT ) | Number KW_PRECEDING -> ^( KW_PRECEDING Number ) );
	public final HiveParser_SelectClauseParser.window_frame_start_boundary_return window_frame_start_boundary() throws RecognitionException {
		HiveParser_SelectClauseParser.window_frame_start_boundary_return retval = new HiveParser_SelectClauseParser.window_frame_start_boundary_return();
		retval.start = input.LT(1);

		ASTNode root_0 = null;

		Token KW_UNBOUNDED73=null;
		Token KW_PRECEDING74=null;
		Token KW_CURRENT75=null;
		Token KW_ROW76=null;
		Token Number77=null;
		Token KW_PRECEDING78=null;

		ASTNode KW_UNBOUNDED73_tree=null;
		ASTNode KW_PRECEDING74_tree=null;
		ASTNode KW_CURRENT75_tree=null;
		ASTNode KW_ROW76_tree=null;
		ASTNode Number77_tree=null;
		ASTNode KW_PRECEDING78_tree=null;
		RewriteRuleTokenStream stream_Number=new RewriteRuleTokenStream(adaptor,"token Number");
		RewriteRuleTokenStream stream_KW_ROW=new RewriteRuleTokenStream(adaptor,"token KW_ROW");
		RewriteRuleTokenStream stream_KW_UNBOUNDED=new RewriteRuleTokenStream(adaptor,"token KW_UNBOUNDED");
		RewriteRuleTokenStream stream_KW_PRECEDING=new RewriteRuleTokenStream(adaptor,"token KW_PRECEDING");
		RewriteRuleTokenStream stream_KW_CURRENT=new RewriteRuleTokenStream(adaptor,"token KW_CURRENT");

		 gParent.pushMsg("windowframestartboundary", state); 
		try {
			// SelectClauseParser.g:166:3: ( KW_UNBOUNDED KW_PRECEDING -> ^( KW_PRECEDING KW_UNBOUNDED ) | KW_CURRENT KW_ROW -> ^( KW_CURRENT ) | Number KW_PRECEDING -> ^( KW_PRECEDING Number ) )
			int alt29=3;
			switch ( input.LA(1) ) {
			case KW_UNBOUNDED:
				{
				alt29=1;
				}
				break;
			case KW_CURRENT:
				{
				alt29=2;
				}
				break;
			case Number:
				{
				alt29=3;
				}
				break;
			default:
				if (state.backtracking>0) {state.failed=true; return retval;}
				NoViableAltException nvae =
					new NoViableAltException("", 29, 0, input);
				throw nvae;
			}
			switch (alt29) {
				case 1 :
					// SelectClauseParser.g:167:3: KW_UNBOUNDED KW_PRECEDING
					{
					KW_UNBOUNDED73=(Token)match(input,KW_UNBOUNDED,FOLLOW_KW_UNBOUNDED_in_window_frame_start_boundary1061); if (state.failed) return retval; 
					if ( state.backtracking==0 ) stream_KW_UNBOUNDED.add(KW_UNBOUNDED73);

					KW_PRECEDING74=(Token)match(input,KW_PRECEDING,FOLLOW_KW_PRECEDING_in_window_frame_start_boundary1063); if (state.failed) return retval; 
					if ( state.backtracking==0 ) stream_KW_PRECEDING.add(KW_PRECEDING74);

					// AST REWRITE
					// elements: KW_PRECEDING, KW_UNBOUNDED
					// token labels: 
					// rule labels: retval
					// token list labels: 
					// rule list labels: 
					// wildcard labels: 
					if ( state.backtracking==0 ) {
					retval.tree = root_0;
					RewriteRuleSubtreeStream stream_retval=new RewriteRuleSubtreeStream(adaptor,"rule retval",retval!=null?retval.getTree():null);

					root_0 = (ASTNode)adaptor.nil();
					// 167:30: -> ^( KW_PRECEDING KW_UNBOUNDED )
					{
						// SelectClauseParser.g:167:33: ^( KW_PRECEDING KW_UNBOUNDED )
						{
						ASTNode root_1 = (ASTNode)adaptor.nil();
						root_1 = (ASTNode)adaptor.becomeRoot(stream_KW_PRECEDING.nextNode(), root_1);
						adaptor.addChild(root_1, stream_KW_UNBOUNDED.nextNode());
						adaptor.addChild(root_0, root_1);
						}

					}


					retval.tree = root_0;
					}

					}
					break;
				case 2 :
					// SelectClauseParser.g:168:3: KW_CURRENT KW_ROW
					{
					KW_CURRENT75=(Token)match(input,KW_CURRENT,FOLLOW_KW_CURRENT_in_window_frame_start_boundary1079); if (state.failed) return retval; 
					if ( state.backtracking==0 ) stream_KW_CURRENT.add(KW_CURRENT75);

					KW_ROW76=(Token)match(input,KW_ROW,FOLLOW_KW_ROW_in_window_frame_start_boundary1081); if (state.failed) return retval; 
					if ( state.backtracking==0 ) stream_KW_ROW.add(KW_ROW76);

					// AST REWRITE
					// elements: KW_CURRENT
					// token labels: 
					// rule labels: retval
					// token list labels: 
					// rule list labels: 
					// wildcard labels: 
					if ( state.backtracking==0 ) {
					retval.tree = root_0;
					RewriteRuleSubtreeStream stream_retval=new RewriteRuleSubtreeStream(adaptor,"rule retval",retval!=null?retval.getTree():null);

					root_0 = (ASTNode)adaptor.nil();
					// 168:22: -> ^( KW_CURRENT )
					{
						// SelectClauseParser.g:168:25: ^( KW_CURRENT )
						{
						ASTNode root_1 = (ASTNode)adaptor.nil();
						root_1 = (ASTNode)adaptor.becomeRoot(stream_KW_CURRENT.nextNode(), root_1);
						adaptor.addChild(root_0, root_1);
						}

					}


					retval.tree = root_0;
					}

					}
					break;
				case 3 :
					// SelectClauseParser.g:169:3: Number KW_PRECEDING
					{
					Number77=(Token)match(input,Number,FOLLOW_Number_in_window_frame_start_boundary1094); if (state.failed) return retval; 
					if ( state.backtracking==0 ) stream_Number.add(Number77);

					KW_PRECEDING78=(Token)match(input,KW_PRECEDING,FOLLOW_KW_PRECEDING_in_window_frame_start_boundary1096); if (state.failed) return retval; 
					if ( state.backtracking==0 ) stream_KW_PRECEDING.add(KW_PRECEDING78);

					// AST REWRITE
					// elements: Number, KW_PRECEDING
					// token labels: 
					// rule labels: retval
					// token list labels: 
					// rule list labels: 
					// wildcard labels: 
					if ( state.backtracking==0 ) {
					retval.tree = root_0;
					RewriteRuleSubtreeStream stream_retval=new RewriteRuleSubtreeStream(adaptor,"rule retval",retval!=null?retval.getTree():null);

					root_0 = (ASTNode)adaptor.nil();
					// 169:23: -> ^( KW_PRECEDING Number )
					{
						// SelectClauseParser.g:169:26: ^( KW_PRECEDING Number )
						{
						ASTNode root_1 = (ASTNode)adaptor.nil();
						root_1 = (ASTNode)adaptor.becomeRoot(stream_KW_PRECEDING.nextNode(), root_1);
						adaptor.addChild(root_1, stream_Number.nextNode());
						adaptor.addChild(root_0, root_1);
						}

					}


					retval.tree = root_0;
					}

					}
					break;

			}
			retval.stop = input.LT(-1);

			if ( state.backtracking==0 ) {
			retval.tree = (ASTNode)adaptor.rulePostProcessing(root_0);
			adaptor.setTokenBoundaries(retval.tree, retval.start, retval.stop);
			}
			if ( state.backtracking==0 ) { gParent.popMsg(state); }
		}

		catch (RecognitionException e) {
		  throw e;
		}

		finally {
			// do for sure before leaving
		}
		return retval;
	}
	// $ANTLR end "window_frame_start_boundary"


	public static class window_frame_boundary_return extends ParserRuleReturnScope {
		ASTNode tree;
		@Override
		public ASTNode getTree() { return tree; }
	};


	// $ANTLR start "window_frame_boundary"
	// SelectClauseParser.g:172:1: window_frame_boundary : ( KW_UNBOUNDED (r= KW_PRECEDING |r= KW_FOLLOWING ) -> ^( $r KW_UNBOUNDED ) | KW_CURRENT KW_ROW -> ^( KW_CURRENT ) | Number (d= KW_PRECEDING |d= KW_FOLLOWING ) -> ^( $d Number ) );
	public final HiveParser_SelectClauseParser.window_frame_boundary_return window_frame_boundary() throws RecognitionException {
		HiveParser_SelectClauseParser.window_frame_boundary_return retval = new HiveParser_SelectClauseParser.window_frame_boundary_return();
		retval.start = input.LT(1);

		ASTNode root_0 = null;

		Token r=null;
		Token d=null;
		Token KW_UNBOUNDED79=null;
		Token KW_CURRENT80=null;
		Token KW_ROW81=null;
		Token Number82=null;

		ASTNode r_tree=null;
		ASTNode d_tree=null;
		ASTNode KW_UNBOUNDED79_tree=null;
		ASTNode KW_CURRENT80_tree=null;
		ASTNode KW_ROW81_tree=null;
		ASTNode Number82_tree=null;
		RewriteRuleTokenStream stream_Number=new RewriteRuleTokenStream(adaptor,"token Number");
		RewriteRuleTokenStream stream_KW_ROW=new RewriteRuleTokenStream(adaptor,"token KW_ROW");
		RewriteRuleTokenStream stream_KW_UNBOUNDED=new RewriteRuleTokenStream(adaptor,"token KW_UNBOUNDED");
		RewriteRuleTokenStream stream_KW_PRECEDING=new RewriteRuleTokenStream(adaptor,"token KW_PRECEDING");
		RewriteRuleTokenStream stream_KW_FOLLOWING=new RewriteRuleTokenStream(adaptor,"token KW_FOLLOWING");
		RewriteRuleTokenStream stream_KW_CURRENT=new RewriteRuleTokenStream(adaptor,"token KW_CURRENT");

		 gParent.pushMsg("windowframeboundary", state); 
		try {
			// SelectClauseParser.g:175:3: ( KW_UNBOUNDED (r= KW_PRECEDING |r= KW_FOLLOWING ) -> ^( $r KW_UNBOUNDED ) | KW_CURRENT KW_ROW -> ^( KW_CURRENT ) | Number (d= KW_PRECEDING |d= KW_FOLLOWING ) -> ^( $d Number ) )
			int alt32=3;
			switch ( input.LA(1) ) {
			case KW_UNBOUNDED:
				{
				alt32=1;
				}
				break;
			case KW_CURRENT:
				{
				alt32=2;
				}
				break;
			case Number:
				{
				alt32=3;
				}
				break;
			default:
				if (state.backtracking>0) {state.failed=true; return retval;}
				NoViableAltException nvae =
					new NoViableAltException("", 32, 0, input);
				throw nvae;
			}
			switch (alt32) {
				case 1 :
					// SelectClauseParser.g:176:3: KW_UNBOUNDED (r= KW_PRECEDING |r= KW_FOLLOWING )
					{
					KW_UNBOUNDED79=(Token)match(input,KW_UNBOUNDED,FOLLOW_KW_UNBOUNDED_in_window_frame_boundary1127); if (state.failed) return retval; 
					if ( state.backtracking==0 ) stream_KW_UNBOUNDED.add(KW_UNBOUNDED79);

					// SelectClauseParser.g:176:16: (r= KW_PRECEDING |r= KW_FOLLOWING )
					int alt30=2;
					int LA30_0 = input.LA(1);
					if ( (LA30_0==KW_PRECEDING) ) {
						alt30=1;
					}
					else if ( (LA30_0==KW_FOLLOWING) ) {
						alt30=2;
					}

					else {
						if (state.backtracking>0) {state.failed=true; return retval;}
						NoViableAltException nvae =
							new NoViableAltException("", 30, 0, input);
						throw nvae;
					}

					switch (alt30) {
						case 1 :
							// SelectClauseParser.g:176:17: r= KW_PRECEDING
							{
							r=(Token)match(input,KW_PRECEDING,FOLLOW_KW_PRECEDING_in_window_frame_boundary1132); if (state.failed) return retval; 
							if ( state.backtracking==0 ) stream_KW_PRECEDING.add(r);

							}
							break;
						case 2 :
							// SelectClauseParser.g:176:32: r= KW_FOLLOWING
							{
							r=(Token)match(input,KW_FOLLOWING,FOLLOW_KW_FOLLOWING_in_window_frame_boundary1136); if (state.failed) return retval; 
							if ( state.backtracking==0 ) stream_KW_FOLLOWING.add(r);

							}
							break;

					}

					// AST REWRITE
					// elements: KW_UNBOUNDED, r
					// token labels: r
					// rule labels: retval
					// token list labels: 
					// rule list labels: 
					// wildcard labels: 
					if ( state.backtracking==0 ) {
					retval.tree = root_0;
					RewriteRuleTokenStream stream_r=new RewriteRuleTokenStream(adaptor,"token r",r);
					RewriteRuleSubtreeStream stream_retval=new RewriteRuleSubtreeStream(adaptor,"rule retval",retval!=null?retval.getTree():null);

					root_0 = (ASTNode)adaptor.nil();
					// 176:49: -> ^( $r KW_UNBOUNDED )
					{
						// SelectClauseParser.g:176:52: ^( $r KW_UNBOUNDED )
						{
						ASTNode root_1 = (ASTNode)adaptor.nil();
						root_1 = (ASTNode)adaptor.becomeRoot(stream_r.nextNode(), root_1);
						adaptor.addChild(root_1, stream_KW_UNBOUNDED.nextNode());
						adaptor.addChild(root_0, root_1);
						}

					}


					retval.tree = root_0;
					}

					}
					break;
				case 2 :
					// SelectClauseParser.g:177:3: KW_CURRENT KW_ROW
					{
					KW_CURRENT80=(Token)match(input,KW_CURRENT,FOLLOW_KW_CURRENT_in_window_frame_boundary1154); if (state.failed) return retval; 
					if ( state.backtracking==0 ) stream_KW_CURRENT.add(KW_CURRENT80);

					KW_ROW81=(Token)match(input,KW_ROW,FOLLOW_KW_ROW_in_window_frame_boundary1156); if (state.failed) return retval; 
					if ( state.backtracking==0 ) stream_KW_ROW.add(KW_ROW81);

					// AST REWRITE
					// elements: KW_CURRENT
					// token labels: 
					// rule labels: retval
					// token list labels: 
					// rule list labels: 
					// wildcard labels: 
					if ( state.backtracking==0 ) {
					retval.tree = root_0;
					RewriteRuleSubtreeStream stream_retval=new RewriteRuleSubtreeStream(adaptor,"rule retval",retval!=null?retval.getTree():null);

					root_0 = (ASTNode)adaptor.nil();
					// 177:22: -> ^( KW_CURRENT )
					{
						// SelectClauseParser.g:177:25: ^( KW_CURRENT )
						{
						ASTNode root_1 = (ASTNode)adaptor.nil();
						root_1 = (ASTNode)adaptor.becomeRoot(stream_KW_CURRENT.nextNode(), root_1);
						adaptor.addChild(root_0, root_1);
						}

					}


					retval.tree = root_0;
					}

					}
					break;
				case 3 :
					// SelectClauseParser.g:178:3: Number (d= KW_PRECEDING |d= KW_FOLLOWING )
					{
					Number82=(Token)match(input,Number,FOLLOW_Number_in_window_frame_boundary1169); if (state.failed) return retval; 
					if ( state.backtracking==0 ) stream_Number.add(Number82);

					// SelectClauseParser.g:178:10: (d= KW_PRECEDING |d= KW_FOLLOWING )
					int alt31=2;
					int LA31_0 = input.LA(1);
					if ( (LA31_0==KW_PRECEDING) ) {
						alt31=1;
					}
					else if ( (LA31_0==KW_FOLLOWING) ) {
						alt31=2;
					}

					else {
						if (state.backtracking>0) {state.failed=true; return retval;}
						NoViableAltException nvae =
							new NoViableAltException("", 31, 0, input);
						throw nvae;
					}

					switch (alt31) {
						case 1 :
							// SelectClauseParser.g:178:11: d= KW_PRECEDING
							{
							d=(Token)match(input,KW_PRECEDING,FOLLOW_KW_PRECEDING_in_window_frame_boundary1174); if (state.failed) return retval; 
							if ( state.backtracking==0 ) stream_KW_PRECEDING.add(d);

							}
							break;
						case 2 :
							// SelectClauseParser.g:178:28: d= KW_FOLLOWING
							{
							d=(Token)match(input,KW_FOLLOWING,FOLLOW_KW_FOLLOWING_in_window_frame_boundary1180); if (state.failed) return retval; 
							if ( state.backtracking==0 ) stream_KW_FOLLOWING.add(d);

							}
							break;

					}

					// AST REWRITE
					// elements: d, Number
					// token labels: d
					// rule labels: retval
					// token list labels: 
					// rule list labels: 
					// wildcard labels: 
					if ( state.backtracking==0 ) {
					retval.tree = root_0;
					RewriteRuleTokenStream stream_d=new RewriteRuleTokenStream(adaptor,"token d",d);
					RewriteRuleSubtreeStream stream_retval=new RewriteRuleSubtreeStream(adaptor,"rule retval",retval!=null?retval.getTree():null);

					root_0 = (ASTNode)adaptor.nil();
					// 178:45: -> ^( $d Number )
					{
						// SelectClauseParser.g:178:48: ^( $d Number )
						{
						ASTNode root_1 = (ASTNode)adaptor.nil();
						root_1 = (ASTNode)adaptor.becomeRoot(stream_d.nextNode(), root_1);
						adaptor.addChild(root_1, stream_Number.nextNode());
						adaptor.addChild(root_0, root_1);
						}

					}


					retval.tree = root_0;
					}

					}
					break;

			}
			retval.stop = input.LT(-1);

			if ( state.backtracking==0 ) {
			retval.tree = (ASTNode)adaptor.rulePostProcessing(root_0);
			adaptor.setTokenBoundaries(retval.tree, retval.start, retval.stop);
			}
			if ( state.backtracking==0 ) { gParent.popMsg(state); }
		}

		catch (RecognitionException e) {
		  throw e;
		}

		finally {
			// do for sure before leaving
		}
		return retval;
	}
	// $ANTLR end "window_frame_boundary"

	// $ANTLR start synpred1_SelectClauseParser
	public final void synpred1_SelectClauseParser_fragment() throws RecognitionException {
		// SelectClauseParser.g:84:5: ( tableAllColumns )
		// SelectClauseParser.g:84:6: tableAllColumns
		{
		pushFollow(FOLLOW_tableAllColumns_in_synpred1_SelectClauseParser421);
		gHiveParser.tableAllColumns();
		state._fsp--;
		if (state.failed) return;

		}

	}
	// $ANTLR end synpred1_SelectClauseParser

	// $ANTLR start synpred2_SelectClauseParser
	public final void synpred2_SelectClauseParser_fragment() throws RecognitionException {
		// SelectClauseParser.g:108:5: ( tableAllColumns )
		// SelectClauseParser.g:108:6: tableAllColumns
		{
		pushFollow(FOLLOW_tableAllColumns_in_synpred2_SelectClauseParser686);
		gHiveParser.tableAllColumns();
		state._fsp--;
		if (state.failed) return;

		}

	}
	// $ANTLR end synpred2_SelectClauseParser

	// Delegated rules

	public final boolean synpred2_SelectClauseParser() {
		state.backtracking++;
		int start = input.mark();
		try {
			synpred2_SelectClauseParser_fragment(); // can never throw exception
		} catch (RecognitionException re) {
			System.err.println("impossible: "+re);
		}
		boolean success = !state.failed;
		input.rewind(start);
		state.backtracking--;
		state.failed=false;
		return success;
	}
	public final boolean synpred1_SelectClauseParser() {
		state.backtracking++;
		int start = input.mark();
		try {
			synpred1_SelectClauseParser_fragment(); // can never throw exception
		} catch (RecognitionException re) {
			System.err.println("impossible: "+re);
		}
		boolean success = !state.failed;
		input.rewind(start);
		state.backtracking--;
		state.failed=false;
		return success;
	}


	protected DFA7 dfa7 = new DFA7(this);
	protected DFA13 dfa13 = new DFA13(this);
	protected DFA12 dfa12 = new DFA12(this);
	protected DFA16 dfa16 = new DFA16(this);
	protected DFA19 dfa19 = new DFA19(this);
	static final String DFA7_eotS =
		"\u008b\uffff";
	static final String DFA7_eofS =
		"\1\uffff\2\3\u0088\uffff";
	static final String DFA7_minS =
		"\1\30\2\11\26\uffff\1\14\51\uffff\1\14\107\uffff";
	static final String DFA7_maxS =
		"\1\u021d\2\u015b\26\uffff\1\u021d\51\uffff\1\u021d\107\uffff";
	static final String DFA7_acceptS =
		"\3\uffff\1\1\27\uffff\1\2\157\uffff";
	static final String DFA7_specialS =
		"\u008b\uffff}>";
	static final String[] DFA7_transitionS = {
			"\1\1\1\uffff\4\2\2\uffff\1\2\1\uffff\1\2\2\uffff\1\2\1\uffff\2\2\5\uffff"+
			"\2\2\1\uffff\2\2\2\uffff\1\2\1\uffff\4\2\1\uffff\2\2\1\uffff\4\2\2\uffff"+
			"\1\2\7\uffff\1\2\1\uffff\1\2\1\uffff\3\2\1\uffff\2\2\1\uffff\3\2\1\uffff"+
			"\4\2\1\uffff\1\2\1\uffff\1\2\1\uffff\2\2\1\uffff\1\2\1\uffff\1\2\2\uffff"+
			"\1\2\1\uffff\3\2\5\uffff\4\2\5\uffff\2\2\3\uffff\1\2\4\uffff\2\2\3\uffff"+
			"\2\2\1\uffff\3\2\6\uffff\3\2\1\uffff\4\2\3\uffff\1\2\1\uffff\3\2\1\uffff"+
			"\5\2\2\uffff\3\2\1\uffff\1\2\1\uffff\2\2\1\uffff\1\2\1\uffff\2\2\1\uffff"+
			"\1\2\1\uffff\1\2\1\uffff\1\2\2\uffff\2\2\4\uffff\2\2\1\uffff\2\2\2\uffff"+
			"\2\2\1\uffff\1\2\3\uffff\1\2\1\uffff\1\2\1\uffff\2\2\1\uffff\1\2\1\uffff"+
			"\3\2\3\uffff\10\2\1\uffff\1\2\2\uffff\2\2\4\uffff\3\2\1\uffff\4\2\1\uffff"+
			"\5\2\1\uffff\4\2\1\uffff\7\2\1\uffff\1\2\1\uffff\3\2\2\uffff\1\2\1\uffff"+
			"\3\2\4\uffff\1\2\1\uffff\1\2\1\uffff\1\2\1\uffff\3\2\1\uffff\2\2\2\uffff"+
			"\3\2\1\uffff\1\2\1\uffff\5\2\2\uffff\1\2\2\uffff\3\2\66\uffff\1\2\42"+
			"\uffff\1\2\52\uffff\1\2\3\uffff\1\2\52\uffff\1\2\3\uffff\1\2\26\uffff"+
			"\1\2\4\uffff\1\2",
			"\1\3\31\uffff\1\33\6\uffff\3\33\11\uffff\1\33\1\3\30\uffff\2\33\2\uffff"+
			"\1\33\14\uffff\1\3\1\33\10\uffff\1\3\17\uffff\1\33\6\uffff\1\3\4\uffff"+
			"\1\3\1\uffff\1\3\13\uffff\1\3\1\33\1\3\13\uffff\1\3\4\uffff\1\3\11\uffff"+
			"\1\31\5\uffff\1\3\22\uffff\1\3\32\uffff\1\3\1\uffff\1\3\22\uffff\1\3"+
			"\4\uffff\1\3\12\uffff\1\33\1\uffff\1\3\7\uffff\2\33\10\uffff\2\33\13"+
			"\uffff\1\3\1\33\16\uffff\1\33\6\uffff\1\3\1\uffff\1\3\25\uffff\1\3",
			"\1\3\31\uffff\1\33\6\uffff\3\33\11\uffff\1\33\1\3\30\uffff\2\33\2\uffff"+
			"\1\33\14\uffff\1\3\1\33\10\uffff\1\3\17\uffff\1\33\6\uffff\1\3\4\uffff"+
			"\1\3\1\uffff\1\3\13\uffff\1\3\1\33\1\3\13\uffff\1\3\4\uffff\1\3\11\uffff"+
			"\1\103\5\uffff\1\3\22\uffff\1\3\32\uffff\1\3\1\uffff\1\3\22\uffff\1\3"+
			"\4\uffff\1\3\12\uffff\1\33\1\uffff\1\3\7\uffff\2\33\10\uffff\2\33\13"+
			"\uffff\1\3\1\33\16\uffff\1\33\6\uffff\1\3\1\uffff\1\3\25\uffff\1\3",
			"",
			"",
			"",
			"",
			"",
			"",
			"",
			"",
			"",
			"",
			"",
			"",
			"",
			"",
			"",
			"",
			"",
			"",
			"",
			"",
			"",
			"",
			"\1\3\13\uffff\6\3\2\uffff\1\3\1\uffff\2\3\1\uffff\1\3\1\uffff\2\3\1"+
			"\uffff\3\3\1\uffff\2\3\1\uffff\5\3\1\uffff\4\3\1\uffff\2\3\1\uffff\4"+
			"\3\2\uffff\1\3\4\uffff\2\3\1\uffff\1\3\1\uffff\5\3\1\uffff\2\3\1\uffff"+
			"\3\3\1\uffff\4\3\1\uffff\3\3\1\uffff\2\3\1\uffff\1\3\1\uffff\1\3\2\uffff"+
			"\5\3\2\uffff\2\3\1\uffff\6\3\3\uffff\2\3\3\uffff\1\3\2\uffff\1\3\1\uffff"+
			"\3\3\2\uffff\2\3\1\uffff\3\3\1\uffff\1\3\1\uffff\1\3\2\uffff\3\3\1\uffff"+
			"\4\3\3\uffff\1\3\1\uffff\3\3\1\uffff\5\3\1\uffff\4\3\1\uffff\1\3\1\uffff"+
			"\2\3\1\uffff\1\3\1\uffff\6\3\1\uffff\1\3\2\uffff\2\3\4\uffff\2\3\1\uffff"+
			"\2\3\2\uffff\2\3\1\uffff\1\3\3\uffff\1\3\1\uffff\1\3\1\uffff\2\3\1\uffff"+
			"\1\3\1\uffff\3\3\3\uffff\10\3\1\uffff\1\3\2\uffff\2\3\4\uffff\3\3\1\uffff"+
			"\4\3\1\uffff\12\3\1\uffff\7\3\1\uffff\1\3\1\uffff\3\3\1\uffff\2\3\1\uffff"+
			"\3\3\2\uffff\1\3\1\uffff\1\3\1\uffff\1\3\1\uffff\1\3\1\uffff\3\3\1\uffff"+
			"\2\3\2\uffff\3\3\1\uffff\1\3\1\uffff\5\3\2\uffff\1\3\2\uffff\3\3\1\uffff"+
			"\1\33\2\uffff\1\3\2\uffff\1\3\2\uffff\3\3\10\uffff\3\3\36\uffff\1\3\42"+
			"\uffff\1\3\52\uffff\1\3\3\uffff\1\3\52\uffff\1\3\3\uffff\1\3\26\uffff"+
			"\1\3\4\uffff\1\3",
			"",
			"",
			"",
			"",
			"",
			"",
			"",
			"",
			"",
			"",
			"",
			"",
			"",
			"",
			"",
			"",
			"",
			"",
			"",
			"",
			"",
			"",
			"",
			"",
			"",
			"",
			"",
			"",
			"",
			"",
			"",
			"",
			"",
			"",
			"",
			"",
			"",
			"",
			"",
			"",
			"",
			"\1\3\13\uffff\6\3\2\uffff\1\3\1\uffff\2\3\1\uffff\1\3\1\uffff\2\3\1"+
			"\uffff\3\3\1\uffff\2\3\1\uffff\5\3\1\uffff\4\3\1\uffff\2\3\1\uffff\4"+
			"\3\2\uffff\1\3\4\uffff\2\3\1\uffff\1\3\1\uffff\5\3\1\uffff\2\3\1\uffff"+
			"\3\3\1\uffff\4\3\1\uffff\3\3\1\uffff\2\3\1\uffff\1\3\1\uffff\1\3\2\uffff"+
			"\5\3\2\uffff\2\3\1\uffff\6\3\3\uffff\2\3\3\uffff\1\3\2\uffff\1\3\1\uffff"+
			"\3\3\2\uffff\2\3\1\uffff\3\3\1\uffff\1\3\1\uffff\1\3\2\uffff\3\3\1\uffff"+
			"\4\3\3\uffff\1\3\1\uffff\3\3\1\uffff\5\3\1\uffff\4\3\1\uffff\1\3\1\uffff"+
			"\2\3\1\uffff\1\3\1\uffff\6\3\1\uffff\1\3\2\uffff\2\3\4\uffff\2\3\1\uffff"+
			"\2\3\2\uffff\2\3\1\uffff\1\3\3\uffff\1\3\1\uffff\1\3\1\uffff\2\3\1\uffff"+
			"\1\3\1\uffff\3\3\3\uffff\10\3\1\uffff\1\3\2\uffff\2\3\4\uffff\3\3\1\uffff"+
			"\4\3\1\uffff\12\3\1\uffff\7\3\1\uffff\1\3\1\uffff\3\3\1\uffff\2\3\1\uffff"+
			"\3\3\2\uffff\1\3\1\uffff\1\3\1\uffff\1\3\1\uffff\1\3\1\uffff\3\3\1\uffff"+
			"\2\3\2\uffff\3\3\1\uffff\1\3\1\uffff\5\3\2\uffff\1\3\2\uffff\3\3\1\uffff"+
			"\1\33\2\uffff\1\3\2\uffff\1\3\2\uffff\3\3\10\uffff\3\3\36\uffff\1\3\42"+
			"\uffff\1\3\52\uffff\1\3\3\uffff\1\3\52\uffff\1\3\3\uffff\1\3\26\uffff"+
			"\1\3\4\uffff\1\3",
			"",
			"",
			"",
			"",
			"",
			"",
			"",
			"",
			"",
			"",
			"",
			"",
			"",
			"",
			"",
			"",
			"",
			"",
			"",
			"",
			"",
			"",
			"",
			"",
			"",
			"",
			"",
			"",
			"",
			"",
			"",
			"",
			"",
			"",
			"",
			"",
			"",
			"",
			"",
			"",
			"",
			"",
			"",
			"",
			"",
			"",
			"",
			"",
			"",
			"",
			"",
			"",
			"",
			"",
			"",
			"",
			"",
			"",
			"",
			"",
			"",
			"",
			"",
			"",
			"",
			"",
			"",
			"",
			"",
			"",
			""
	};

	static final short[] DFA7_eot = DFA.unpackEncodedString(DFA7_eotS);
	static final short[] DFA7_eof = DFA.unpackEncodedString(DFA7_eofS);
	static final char[] DFA7_min = DFA.unpackEncodedStringToUnsignedChars(DFA7_minS);
	static final char[] DFA7_max = DFA.unpackEncodedStringToUnsignedChars(DFA7_maxS);
	static final short[] DFA7_accept = DFA.unpackEncodedString(DFA7_acceptS);
	static final short[] DFA7_special = DFA.unpackEncodedString(DFA7_specialS);
	static final short[][] DFA7_transition;

	static {
		int numStates = DFA7_transitionS.length;
		DFA7_transition = new short[numStates][];
		for (int i=0; i<numStates; i++) {
			DFA7_transition[i] = DFA.unpackEncodedString(DFA7_transitionS[i]);
		}
	}

	protected class DFA7 extends DFA {

		public DFA7(BaseRecognizer recognizer) {
			this.recognizer = recognizer;
			this.decisionNumber = 7;
			this.eot = DFA7_eot;
			this.eof = DFA7_eof;
			this.min = DFA7_min;
			this.max = DFA7_max;
			this.accept = DFA7_accept;
			this.special = DFA7_special;
			this.transition = DFA7_transition;
		}
		@Override
		public String getDescription() {
			return "75:65: ( aliasList | columnNameTypeList )";
		}
	}

	static final String DFA13_eotS =
		"\174\uffff";
	static final String DFA13_eofS =
		"\2\uffff\2\4\170\uffff";
	static final String DFA13_minS =
		"\1\14\1\uffff\2\4\26\uffff\1\30\55\uffff\1\30\56\uffff\2\0\1\uffff\2\0";
	static final String DFA13_maxS =
		"\1\u021d\1\uffff\2\u021d\26\uffff\1\u021d\55\uffff\1\u021d\56\uffff\2"+
		"\0\1\uffff\2\0";
	static final String DFA13_acceptS =
		"\1\uffff\1\1\2\uffff\1\2\161\uffff\1\1\2\uffff\1\1\2\uffff";
	static final String DFA13_specialS =
		"\1\0\31\uffff\1\1\55\uffff\1\2\56\uffff\1\3\1\4\1\uffff\1\5\1\6}>";
	static final String[] DFA13_transitionS = {
			"\1\4\13\uffff\1\2\1\4\4\3\2\uffff\1\3\1\uffff\1\3\1\4\1\uffff\1\3\1\uffff"+
			"\2\3\1\uffff\3\4\1\uffff\2\3\1\uffff\2\3\2\4\1\3\1\uffff\4\3\1\uffff"+
			"\2\3\1\uffff\4\3\2\uffff\1\3\4\uffff\2\4\1\uffff\1\3\1\uffff\1\3\1\4"+
			"\3\3\1\uffff\2\3\1\uffff\3\3\1\uffff\4\3\1\uffff\1\3\1\4\1\3\1\uffff"+
			"\2\3\1\uffff\1\3\1\uffff\1\3\2\uffff\1\3\1\4\3\3\2\uffff\2\4\1\uffff"+
			"\4\3\2\4\3\uffff\2\3\3\uffff\1\3\2\uffff\1\4\1\uffff\2\3\1\4\2\uffff"+
			"\2\3\1\uffff\3\3\1\uffff\1\4\1\uffff\1\4\2\uffff\3\3\1\uffff\4\3\3\uffff"+
			"\1\3\1\uffff\3\3\1\uffff\5\3\1\uffff\1\4\3\3\1\uffff\1\3\1\uffff\2\3"+
			"\1\uffff\1\3\1\uffff\2\3\1\4\1\3\1\4\1\3\1\uffff\1\3\2\uffff\2\3\4\uffff"+
			"\2\3\1\uffff\2\3\2\uffff\2\3\1\uffff\1\3\3\uffff\1\3\1\uffff\1\3\1\uffff"+
			"\2\3\1\uffff\1\3\1\uffff\3\3\3\uffff\10\3\1\uffff\1\3\2\uffff\2\3\4\uffff"+
			"\3\3\1\uffff\4\3\1\uffff\5\3\1\4\4\3\1\uffff\7\3\1\uffff\1\3\1\uffff"+
			"\3\3\1\uffff\1\4\1\3\1\uffff\3\3\2\uffff\1\4\1\uffff\1\3\1\uffff\1\3"+
			"\1\uffff\1\3\1\uffff\3\3\1\uffff\2\3\2\uffff\3\3\1\uffff\1\3\1\uffff"+
			"\5\3\2\uffff\1\3\2\uffff\3\3\4\uffff\1\4\2\uffff\1\4\2\uffff\3\4\10\uffff"+
			"\1\1\2\4\36\uffff\1\3\42\uffff\1\3\52\uffff\1\3\3\uffff\1\3\52\uffff"+
			"\1\3\3\uffff\1\3\26\uffff\1\3\4\uffff\1\3",
			"",
			"\3\4\2\uffff\2\4\2\uffff\2\4\1\uffff\1\32\1\uffff\2\4\1\uffff\2\4\1"+
			"\uffff\1\4\1\uffff\4\4\2\uffff\3\4\1\uffff\2\4\1\uffff\3\4\4\uffff\2"+
			"\4\1\uffff\2\4\2\uffff\1\4\1\uffff\4\4\1\uffff\2\4\1\uffff\4\4\2\uffff"+
			"\1\4\7\uffff\1\4\1\uffff\1\4\1\uffff\3\4\1\uffff\2\4\1\uffff\3\4\1\uffff"+
			"\4\4\1\uffff\1\4\1\uffff\1\4\1\uffff\2\4\1\uffff\1\4\1\uffff\2\4\1\uffff"+
			"\1\4\1\uffff\3\4\5\uffff\4\4\5\uffff\3\4\2\uffff\1\4\1\uffff\1\4\1\uffff"+
			"\3\4\2\uffff\3\4\1\uffff\4\4\1\uffff\1\4\2\uffff\4\4\1\uffff\5\4\2\uffff"+
			"\5\4\1\uffff\5\4\1\uffff\4\4\1\uffff\4\4\1\uffff\1\4\1\uffff\4\4\1\uffff"+
			"\1\4\1\uffff\1\4\2\uffff\4\4\2\uffff\2\4\1\uffff\2\4\2\uffff\2\4\1\uffff"+
			"\1\4\3\uffff\1\4\1\uffff\1\4\1\uffff\2\4\1\uffff\1\4\1\uffff\4\4\1\uffff"+
			"\11\4\1\uffff\1\4\1\uffff\3\4\4\uffff\10\4\1\uffff\5\4\1\uffff\4\4\1"+
			"\uffff\7\4\1\uffff\1\4\1\uffff\3\4\2\uffff\1\4\1\uffff\3\4\4\uffff\1"+
			"\4\1\uffff\3\4\1\uffff\3\4\1\uffff\2\4\2\uffff\3\4\1\uffff\1\4\1\uffff"+
			"\5\4\1\uffff\3\4\1\uffff\3\4\1\uffff\2\4\1\uffff\2\4\1\uffff\3\4\2\uffff"+
			"\1\4\4\uffff\1\4\3\uffff\1\4\40\uffff\1\4\42\uffff\1\4\52\uffff\1\4\3"+
			"\uffff\1\4\52\uffff\1\4\3\uffff\1\4\26\uffff\1\4\4\uffff\1\4",
			"\3\4\2\uffff\2\4\2\uffff\2\4\1\uffff\1\110\1\uffff\2\4\1\uffff\2\4\1"+
			"\uffff\1\4\1\uffff\4\4\2\uffff\3\4\1\uffff\2\4\1\uffff\3\4\4\uffff\2"+
			"\4\1\uffff\2\4\2\uffff\1\4\1\uffff\4\4\1\uffff\2\4\1\uffff\4\4\2\uffff"+
			"\1\4\7\uffff\1\4\1\uffff\1\4\1\uffff\3\4\1\uffff\2\4\1\uffff\3\4\1\uffff"+
			"\4\4\1\uffff\1\4\1\uffff\1\4\1\uffff\2\4\1\uffff\1\4\1\uffff\2\4\1\uffff"+
			"\1\4\1\uffff\3\4\5\uffff\4\4\5\uffff\3\4\2\uffff\1\4\1\uffff\1\4\1\uffff"+
			"\3\4\2\uffff\3\4\1\uffff\4\4\1\uffff\1\4\2\uffff\4\4\1\uffff\5\4\2\uffff"+
			"\5\4\1\uffff\5\4\1\uffff\4\4\1\uffff\4\4\1\uffff\1\4\1\uffff\4\4\1\uffff"+
			"\1\4\1\uffff\1\4\2\uffff\4\4\2\uffff\2\4\1\uffff\2\4\2\uffff\2\4\1\uffff"+
			"\1\4\3\uffff\1\4\1\uffff\1\4\1\uffff\2\4\1\uffff\1\4\1\uffff\4\4\1\uffff"+
			"\11\4\1\uffff\1\4\1\uffff\3\4\4\uffff\10\4\1\uffff\5\4\1\uffff\4\4\1"+
			"\uffff\7\4\1\uffff\1\4\1\uffff\3\4\2\uffff\1\4\1\uffff\3\4\4\uffff\1"+
			"\4\1\uffff\3\4\1\uffff\3\4\1\uffff\2\4\2\uffff\3\4\1\uffff\1\4\1\uffff"+
			"\5\4\1\uffff\3\4\1\uffff\3\4\1\uffff\2\4\1\uffff\2\4\1\uffff\3\4\2\uffff"+
			"\1\4\4\uffff\1\4\3\uffff\1\4\40\uffff\1\4\42\uffff\1\4\52\uffff\1\4\3"+
			"\uffff\1\4\52\uffff\1\4\3\uffff\1\4\26\uffff\1\4\4\uffff\1\4",
			"",
			"",
			"",
			"",
			"",
			"",
			"",
			"",
			"",
			"",
			"",
			"",
			"",
			"",
			"",
			"",
			"",
			"",
			"",
			"",
			"",
			"",
			"\1\167\1\uffff\4\170\2\uffff\1\170\1\uffff\1\170\2\uffff\1\170\1\uffff"+
			"\2\170\5\uffff\2\170\1\uffff\2\170\2\uffff\1\170\1\uffff\4\170\1\uffff"+
			"\2\170\1\uffff\4\170\2\uffff\1\170\7\uffff\1\170\1\uffff\1\170\1\uffff"+
			"\3\170\1\uffff\2\170\1\uffff\3\170\1\uffff\4\170\1\uffff\1\170\1\uffff"+
			"\1\170\1\uffff\2\170\1\uffff\1\170\1\uffff\1\170\2\uffff\1\170\1\uffff"+
			"\3\170\5\uffff\4\170\5\uffff\2\170\3\uffff\1\170\4\uffff\2\170\3\uffff"+
			"\2\170\1\uffff\3\170\6\uffff\3\170\1\uffff\4\170\3\uffff\1\170\1\uffff"+
			"\3\170\1\uffff\5\170\2\uffff\3\170\1\uffff\1\170\1\uffff\2\170\1\uffff"+
			"\1\170\1\uffff\2\170\1\uffff\1\170\1\uffff\1\170\1\uffff\1\170\2\uffff"+
			"\2\170\4\uffff\2\170\1\uffff\2\170\2\uffff\2\170\1\uffff\1\170\3\uffff"+
			"\1\170\1\uffff\1\170\1\uffff\2\170\1\uffff\1\170\1\uffff\3\170\3\uffff"+
			"\10\170\1\uffff\1\170\2\uffff\2\170\4\uffff\3\170\1\uffff\4\170\1\uffff"+
			"\5\170\1\uffff\4\170\1\uffff\7\170\1\uffff\1\170\1\uffff\3\170\2\uffff"+
			"\1\170\1\uffff\3\170\4\uffff\1\170\1\uffff\1\170\1\uffff\1\170\1\uffff"+
			"\3\170\1\uffff\2\170\2\uffff\3\170\1\uffff\1\170\1\uffff\5\170\2\uffff"+
			"\1\170\2\uffff\3\170\25\uffff\1\166\40\uffff\1\170\42\uffff\1\170\52"+
			"\uffff\1\170\3\uffff\1\170\52\uffff\1\170\3\uffff\1\170\26\uffff\1\170"+
			"\4\uffff\1\170",
			"",
			"",
			"",
			"",
			"",
			"",
			"",
			"",
			"",
			"",
			"",
			"",
			"",
			"",
			"",
			"",
			"",
			"",
			"",
			"",
			"",
			"",
			"",
			"",
			"",
			"",
			"",
			"",
			"",
			"",
			"",
			"",
			"",
			"",
			"",
			"",
			"",
			"",
			"",
			"",
			"",
			"",
			"",
			"",
			"",
			"\1\172\1\uffff\4\173\2\uffff\1\173\1\uffff\1\173\2\uffff\1\173\1\uffff"+
			"\2\173\5\uffff\2\173\1\uffff\2\173\2\uffff\1\173\1\uffff\4\173\1\uffff"+
			"\2\173\1\uffff\4\173\2\uffff\1\173\7\uffff\1\173\1\uffff\1\173\1\uffff"+
			"\3\173\1\uffff\2\173\1\uffff\3\173\1\uffff\4\173\1\uffff\1\173\1\uffff"+
			"\1\173\1\uffff\2\173\1\uffff\1\173\1\uffff\1\173\2\uffff\1\173\1\uffff"+
			"\3\173\5\uffff\4\173\5\uffff\2\173\3\uffff\1\173\4\uffff\2\173\3\uffff"+
			"\2\173\1\uffff\3\173\6\uffff\3\173\1\uffff\4\173\3\uffff\1\173\1\uffff"+
			"\3\173\1\uffff\5\173\2\uffff\3\173\1\uffff\1\173\1\uffff\2\173\1\uffff"+
			"\1\173\1\uffff\2\173\1\uffff\1\173\1\uffff\1\173\1\uffff\1\173\2\uffff"+
			"\2\173\4\uffff\2\173\1\uffff\2\173\2\uffff\2\173\1\uffff\1\173\3\uffff"+
			"\1\173\1\uffff\1\173\1\uffff\2\173\1\uffff\1\173\1\uffff\3\173\3\uffff"+
			"\10\173\1\uffff\1\173\2\uffff\2\173\4\uffff\3\173\1\uffff\4\173\1\uffff"+
			"\5\173\1\uffff\4\173\1\uffff\7\173\1\uffff\1\173\1\uffff\3\173\2\uffff"+
			"\1\173\1\uffff\3\173\4\uffff\1\173\1\uffff\1\173\1\uffff\1\173\1\uffff"+
			"\3\173\1\uffff\2\173\2\uffff\3\173\1\uffff\1\173\1\uffff\5\173\2\uffff"+
			"\1\173\2\uffff\3\173\25\uffff\1\171\40\uffff\1\173\42\uffff\1\173\52"+
			"\uffff\1\173\3\uffff\1\173\52\uffff\1\173\3\uffff\1\173\26\uffff\1\173"+
			"\4\uffff\1\173",
			"",
			"",
			"",
			"",
			"",
			"",
			"",
			"",
			"",
			"",
			"",
			"",
			"",
			"",
			"",
			"",
			"",
			"",
			"",
			"",
			"",
			"",
			"",
			"",
			"",
			"",
			"",
			"",
			"",
			"",
			"",
			"",
			"",
			"",
			"",
			"",
			"",
			"",
			"",
			"",
			"",
			"",
			"",
			"",
			"",
			"",
			"\1\uffff",
			"\1\uffff",
			"",
			"\1\uffff",
			"\1\uffff"
	};

	static final short[] DFA13_eot = DFA.unpackEncodedString(DFA13_eotS);
	static final short[] DFA13_eof = DFA.unpackEncodedString(DFA13_eofS);
	static final char[] DFA13_min = DFA.unpackEncodedStringToUnsignedChars(DFA13_minS);
	static final char[] DFA13_max = DFA.unpackEncodedStringToUnsignedChars(DFA13_maxS);
	static final short[] DFA13_accept = DFA.unpackEncodedString(DFA13_acceptS);
	static final short[] DFA13_special = DFA.unpackEncodedString(DFA13_specialS);
	static final short[][] DFA13_transition;

	static {
		int numStates = DFA13_transitionS.length;
		DFA13_transition = new short[numStates][];
		for (int i=0; i<numStates; i++) {
			DFA13_transition[i] = DFA.unpackEncodedString(DFA13_transitionS[i]);
		}
	}

	protected class DFA13 extends DFA {

		public DFA13(BaseRecognizer recognizer) {
			this.recognizer = recognizer;
			this.decisionNumber = 13;
			this.eot = DFA13_eot;
			this.eof = DFA13_eof;
			this.min = DFA13_min;
			this.max = DFA13_max;
			this.accept = DFA13_accept;
			this.special = DFA13_special;
			this.transition = DFA13_transition;
		}
		@Override
		public String getDescription() {
			return "80:1: selectItem : ( ( tableAllColumns )=> tableAllColumns -> ^( TOK_SELEXPR tableAllColumns ) | ( expression ( ( ( KW_AS )? identifier ) | ( KW_AS LPAREN identifier ( COMMA identifier )* RPAREN ) )? ) -> ^( TOK_SELEXPR expression ( identifier )* ) );";
		}
		@Override
		public int specialStateTransition(int s, IntStream _input) throws NoViableAltException {
			TokenStream input = (TokenStream)_input;
			int _s = s;
			switch ( s ) {
					case 0 : 
						int LA13_0 = input.LA(1);
						 
						int index13_0 = input.index();
						input.rewind();
						s = -1;
						if ( (LA13_0==STAR) && (synpred1_SelectClauseParser())) {s = 1;}
						else if ( (LA13_0==Identifier) ) {s = 2;}
						else if ( ((LA13_0 >= KW_ABORT && LA13_0 <= KW_AFTER)||LA13_0==KW_ANALYZE||LA13_0==KW_ARCHIVE||LA13_0==KW_ASC||(LA13_0 >= KW_AUTOCOMMIT && LA13_0 <= KW_BEFORE)||(LA13_0 >= KW_BUCKET && LA13_0 <= KW_BUCKETS)||(LA13_0 >= KW_CACHE && LA13_0 <= KW_CASCADE)||LA13_0==KW_CHANGE||(LA13_0 >= KW_CLUSTER && LA13_0 <= KW_COLLECTION)||(LA13_0 >= KW_COLUMNS && LA13_0 <= KW_COMMENT)||(LA13_0 >= KW_COMPACT && LA13_0 <= KW_CONCATENATE)||LA13_0==KW_CONTINUE||LA13_0==KW_DATA||LA13_0==KW_DATABASES||(LA13_0 >= KW_DATETIME && LA13_0 <= KW_DBPROPERTIES)||(LA13_0 >= KW_DEFERRED && LA13_0 <= KW_DEFINED)||(LA13_0 >= KW_DELIMITED && LA13_0 <= KW_DESC)||(LA13_0 >= KW_DETAIL && LA13_0 <= KW_DISABLE)||LA13_0==KW_DISTRIBUTE||LA13_0==KW_DOW||(LA13_0 >= KW_DUMP && LA13_0 <= KW_ELEM_TYPE)||LA13_0==KW_ENABLE||LA13_0==KW_ESCAPED||LA13_0==KW_EXCLUSIVE||(LA13_0 >= KW_EXPLAIN && LA13_0 <= KW_EXPRESSION)||(LA13_0 >= KW_FIELDS && LA13_0 <= KW_FIRST)||(LA13_0 >= KW_FORMAT && LA13_0 <= KW_FORMATTED)||LA13_0==KW_FUNCTIONS||(LA13_0 >= KW_HOUR && LA13_0 <= KW_IDXPROPERTIES)||(LA13_0 >= KW_INDEX && LA13_0 <= KW_INDEXES)||(LA13_0 >= KW_INPATH && LA13_0 <= KW_INPUTFORMAT)||(LA13_0 >= KW_ISOLATION && LA13_0 <= KW_JAR)||(LA13_0 >= KW_KEY && LA13_0 <= KW_LAST)||LA13_0==KW_LEVEL||(LA13_0 >= KW_LIMIT && LA13_0 <= KW_LOAD)||(LA13_0 >= KW_LOCATION && LA13_0 <= KW_LONG)||(LA13_0 >= KW_MAPJOIN && LA13_0 <= KW_MATERIALIZED)||LA13_0==KW_METADATA||(LA13_0 >= KW_MINUTE && LA13_0 <= KW_MONTH)||LA13_0==KW_MSCK||(LA13_0 >= KW_NORELY && LA13_0 <= KW_NOSCAN)||LA13_0==KW_NOVALIDATE||LA13_0==KW_NULLS||LA13_0==KW_OFFSET||(LA13_0 >= KW_OPERATOR && LA13_0 <= KW_OPTION)||(LA13_0 >= KW_OUTPUTDRIVER && LA13_0 <= KW_OUTPUTFORMAT)||(LA13_0 >= KW_OVERWRITE && LA13_0 <= KW_OWNER)||(LA13_0 >= KW_PARTITIONED && LA13_0 <= KW_PARTITIONS)||LA13_0==KW_PLUS||LA13_0==KW_PRETTY||LA13_0==KW_PRINCIPALS||(LA13_0 >= KW_PURGE && LA13_0 <= KW_QUARTER)||LA13_0==KW_READ||(LA13_0 >= KW_REBUILD && LA13_0 <= KW_RECORDWRITER)||(LA13_0 >= KW_RELOAD && LA13_0 <= KW_RESTRICT)||LA13_0==KW_REWRITE||(LA13_0 >= KW_ROLE && LA13_0 <= KW_ROLES)||(LA13_0 >= KW_SCHEMA && LA13_0 <= KW_SECOND)||(LA13_0 >= KW_SEMI && LA13_0 <= KW_SERVER)||(LA13_0 >= KW_SETS && LA13_0 <= KW_SKEWED)||(LA13_0 >= KW_SNAPSHOT && LA13_0 <= KW_SSL)||(LA13_0 >= KW_STATISTICS && LA13_0 <= KW_SUMMARY)||LA13_0==KW_TABLES||(LA13_0 >= KW_TBLPROPERTIES && LA13_0 <= KW_TERMINATED)||LA13_0==KW_TINYINT||(LA13_0 >= KW_TOUCH && LA13_0 <= KW_TRANSACTIONS)||LA13_0==KW_UNARCHIVE||LA13_0==KW_UNDO||LA13_0==KW_UNIONTYPE||(LA13_0 >= KW_UNLOCK && LA13_0 <= KW_UNSIGNED)||(LA13_0 >= KW_URI && LA13_0 <= KW_USE)||(LA13_0 >= KW_UTC && LA13_0 <= KW_VALIDATE)||LA13_0==KW_VALUE_TYPE||(LA13_0 >= KW_VECTORIZATION && LA13_0 <= KW_WEEK)||LA13_0==KW_WHILE||(LA13_0 >= KW_WORK && LA13_0 <= KW_YEAR)||LA13_0==KW_BATCH||LA13_0==KW_DAYOFWEEK||LA13_0==KW_HOLD_DDLTIME||LA13_0==KW_IGNORE||LA13_0==KW_NO_DROP||LA13_0==KW_OFFLINE||LA13_0==KW_PROTECTION||LA13_0==KW_READONLY) ) {s = 3;}
						else if ( (LA13_0==CharSetName||LA13_0==IntegralLiteral||LA13_0==KW_ARRAY||(LA13_0 >= KW_BIGINT && LA13_0 <= KW_BOOLEAN)||(LA13_0 >= KW_CASE && LA13_0 <= KW_CAST)||(LA13_0 >= KW_CURRENT_DATE && LA13_0 <= KW_CURRENT_TIMESTAMP)||LA13_0==KW_DATE||LA13_0==KW_DOUBLE||LA13_0==KW_EXISTS||(LA13_0 >= KW_EXTRACT && LA13_0 <= KW_FALSE)||(LA13_0 >= KW_FLOAT && LA13_0 <= KW_FLOOR)||LA13_0==KW_GROUPING||LA13_0==KW_IF||LA13_0==KW_INT||LA13_0==KW_INTERVAL||LA13_0==KW_MAP||LA13_0==KW_NOT||LA13_0==KW_NULL||LA13_0==KW_SMALLINT||LA13_0==KW_TIMESTAMP||LA13_0==KW_TRUE||LA13_0==LPAREN||LA13_0==MINUS||(LA13_0 >= Number && LA13_0 <= PLUS)||(LA13_0 >= StringLiteral && LA13_0 <= TILDE)) ) {s = 4;}
						 
						input.seek(index13_0);
						if ( s>=0 ) return s;
						break;

					case 1 : 
						int LA13_26 = input.LA(1);
						 
						int index13_26 = input.index();
						input.rewind();
						s = -1;
						if ( (LA13_26==STAR) && (synpred1_SelectClauseParser())) {s = 118;}
						else if ( (LA13_26==Identifier) ) {s = 119;}
						else if ( ((LA13_26 >= KW_ABORT && LA13_26 <= KW_AFTER)||LA13_26==KW_ANALYZE||LA13_26==KW_ARCHIVE||LA13_26==KW_ASC||(LA13_26 >= KW_AUTOCOMMIT && LA13_26 <= KW_BEFORE)||(LA13_26 >= KW_BUCKET && LA13_26 <= KW_BUCKETS)||(LA13_26 >= KW_CACHE && LA13_26 <= KW_CASCADE)||LA13_26==KW_CHANGE||(LA13_26 >= KW_CLUSTER && LA13_26 <= KW_COLLECTION)||(LA13_26 >= KW_COLUMNS && LA13_26 <= KW_COMMENT)||(LA13_26 >= KW_COMPACT && LA13_26 <= KW_CONCATENATE)||LA13_26==KW_CONTINUE||LA13_26==KW_DATA||LA13_26==KW_DATABASES||(LA13_26 >= KW_DATETIME && LA13_26 <= KW_DBPROPERTIES)||(LA13_26 >= KW_DEFERRED && LA13_26 <= KW_DEFINED)||(LA13_26 >= KW_DELIMITED && LA13_26 <= KW_DESC)||(LA13_26 >= KW_DETAIL && LA13_26 <= KW_DISABLE)||LA13_26==KW_DISTRIBUTE||LA13_26==KW_DOW||(LA13_26 >= KW_DUMP && LA13_26 <= KW_ELEM_TYPE)||LA13_26==KW_ENABLE||LA13_26==KW_ESCAPED||LA13_26==KW_EXCLUSIVE||(LA13_26 >= KW_EXPLAIN && LA13_26 <= KW_EXPRESSION)||(LA13_26 >= KW_FIELDS && LA13_26 <= KW_FIRST)||(LA13_26 >= KW_FORMAT && LA13_26 <= KW_FORMATTED)||LA13_26==KW_FUNCTIONS||(LA13_26 >= KW_HOUR && LA13_26 <= KW_IDXPROPERTIES)||(LA13_26 >= KW_INDEX && LA13_26 <= KW_INDEXES)||(LA13_26 >= KW_INPATH && LA13_26 <= KW_INPUTFORMAT)||(LA13_26 >= KW_ISOLATION && LA13_26 <= KW_JAR)||(LA13_26 >= KW_KEY && LA13_26 <= KW_LAST)||LA13_26==KW_LEVEL||(LA13_26 >= KW_LIMIT && LA13_26 <= KW_LOAD)||(LA13_26 >= KW_LOCATION && LA13_26 <= KW_LONG)||(LA13_26 >= KW_MAPJOIN && LA13_26 <= KW_MATERIALIZED)||LA13_26==KW_METADATA||(LA13_26 >= KW_MINUTE && LA13_26 <= KW_MONTH)||LA13_26==KW_MSCK||(LA13_26 >= KW_NORELY && LA13_26 <= KW_NOSCAN)||LA13_26==KW_NOVALIDATE||LA13_26==KW_NULLS||LA13_26==KW_OFFSET||(LA13_26 >= KW_OPERATOR && LA13_26 <= KW_OPTION)||(LA13_26 >= KW_OUTPUTDRIVER && LA13_26 <= KW_OUTPUTFORMAT)||(LA13_26 >= KW_OVERWRITE && LA13_26 <= KW_OWNER)||(LA13_26 >= KW_PARTITIONED && LA13_26 <= KW_PARTITIONS)||LA13_26==KW_PLUS||LA13_26==KW_PRETTY||LA13_26==KW_PRINCIPALS||(LA13_26 >= KW_PURGE && LA13_26 <= KW_QUARTER)||LA13_26==KW_READ||(LA13_26 >= KW_REBUILD && LA13_26 <= KW_RECORDWRITER)||(LA13_26 >= KW_RELOAD && LA13_26 <= KW_RESTRICT)||LA13_26==KW_REWRITE||(LA13_26 >= KW_ROLE && LA13_26 <= KW_ROLES)||(LA13_26 >= KW_SCHEMA && LA13_26 <= KW_SECOND)||(LA13_26 >= KW_SEMI && LA13_26 <= KW_SERVER)||(LA13_26 >= KW_SETS && LA13_26 <= KW_SKEWED)||(LA13_26 >= KW_SNAPSHOT && LA13_26 <= KW_SSL)||(LA13_26 >= KW_STATISTICS && LA13_26 <= KW_SUMMARY)||LA13_26==KW_TABLES||(LA13_26 >= KW_TBLPROPERTIES && LA13_26 <= KW_TERMINATED)||LA13_26==KW_TINYINT||(LA13_26 >= KW_TOUCH && LA13_26 <= KW_TRANSACTIONS)||LA13_26==KW_UNARCHIVE||LA13_26==KW_UNDO||LA13_26==KW_UNIONTYPE||(LA13_26 >= KW_UNLOCK && LA13_26 <= KW_UNSIGNED)||(LA13_26 >= KW_URI && LA13_26 <= KW_USE)||(LA13_26 >= KW_UTC && LA13_26 <= KW_VALIDATE)||LA13_26==KW_VALUE_TYPE||(LA13_26 >= KW_VECTORIZATION && LA13_26 <= KW_WEEK)||LA13_26==KW_WHILE||(LA13_26 >= KW_WORK && LA13_26 <= KW_YEAR)||LA13_26==KW_BATCH||LA13_26==KW_DAYOFWEEK||LA13_26==KW_HOLD_DDLTIME||LA13_26==KW_IGNORE||LA13_26==KW_NO_DROP||LA13_26==KW_OFFLINE||LA13_26==KW_PROTECTION||LA13_26==KW_READONLY) ) {s = 120;}
						 
						input.seek(index13_26);
						if ( s>=0 ) return s;
						break;

					case 2 : 
						int LA13_72 = input.LA(1);
						 
						int index13_72 = input.index();
						input.rewind();
						s = -1;
						if ( (LA13_72==STAR) && (synpred1_SelectClauseParser())) {s = 121;}
						else if ( (LA13_72==Identifier) ) {s = 122;}
						else if ( ((LA13_72 >= KW_ABORT && LA13_72 <= KW_AFTER)||LA13_72==KW_ANALYZE||LA13_72==KW_ARCHIVE||LA13_72==KW_ASC||(LA13_72 >= KW_AUTOCOMMIT && LA13_72 <= KW_BEFORE)||(LA13_72 >= KW_BUCKET && LA13_72 <= KW_BUCKETS)||(LA13_72 >= KW_CACHE && LA13_72 <= KW_CASCADE)||LA13_72==KW_CHANGE||(LA13_72 >= KW_CLUSTER && LA13_72 <= KW_COLLECTION)||(LA13_72 >= KW_COLUMNS && LA13_72 <= KW_COMMENT)||(LA13_72 >= KW_COMPACT && LA13_72 <= KW_CONCATENATE)||LA13_72==KW_CONTINUE||LA13_72==KW_DATA||LA13_72==KW_DATABASES||(LA13_72 >= KW_DATETIME && LA13_72 <= KW_DBPROPERTIES)||(LA13_72 >= KW_DEFERRED && LA13_72 <= KW_DEFINED)||(LA13_72 >= KW_DELIMITED && LA13_72 <= KW_DESC)||(LA13_72 >= KW_DETAIL && LA13_72 <= KW_DISABLE)||LA13_72==KW_DISTRIBUTE||LA13_72==KW_DOW||(LA13_72 >= KW_DUMP && LA13_72 <= KW_ELEM_TYPE)||LA13_72==KW_ENABLE||LA13_72==KW_ESCAPED||LA13_72==KW_EXCLUSIVE||(LA13_72 >= KW_EXPLAIN && LA13_72 <= KW_EXPRESSION)||(LA13_72 >= KW_FIELDS && LA13_72 <= KW_FIRST)||(LA13_72 >= KW_FORMAT && LA13_72 <= KW_FORMATTED)||LA13_72==KW_FUNCTIONS||(LA13_72 >= KW_HOUR && LA13_72 <= KW_IDXPROPERTIES)||(LA13_72 >= KW_INDEX && LA13_72 <= KW_INDEXES)||(LA13_72 >= KW_INPATH && LA13_72 <= KW_INPUTFORMAT)||(LA13_72 >= KW_ISOLATION && LA13_72 <= KW_JAR)||(LA13_72 >= KW_KEY && LA13_72 <= KW_LAST)||LA13_72==KW_LEVEL||(LA13_72 >= KW_LIMIT && LA13_72 <= KW_LOAD)||(LA13_72 >= KW_LOCATION && LA13_72 <= KW_LONG)||(LA13_72 >= KW_MAPJOIN && LA13_72 <= KW_MATERIALIZED)||LA13_72==KW_METADATA||(LA13_72 >= KW_MINUTE && LA13_72 <= KW_MONTH)||LA13_72==KW_MSCK||(LA13_72 >= KW_NORELY && LA13_72 <= KW_NOSCAN)||LA13_72==KW_NOVALIDATE||LA13_72==KW_NULLS||LA13_72==KW_OFFSET||(LA13_72 >= KW_OPERATOR && LA13_72 <= KW_OPTION)||(LA13_72 >= KW_OUTPUTDRIVER && LA13_72 <= KW_OUTPUTFORMAT)||(LA13_72 >= KW_OVERWRITE && LA13_72 <= KW_OWNER)||(LA13_72 >= KW_PARTITIONED && LA13_72 <= KW_PARTITIONS)||LA13_72==KW_PLUS||LA13_72==KW_PRETTY||LA13_72==KW_PRINCIPALS||(LA13_72 >= KW_PURGE && LA13_72 <= KW_QUARTER)||LA13_72==KW_READ||(LA13_72 >= KW_REBUILD && LA13_72 <= KW_RECORDWRITER)||(LA13_72 >= KW_RELOAD && LA13_72 <= KW_RESTRICT)||LA13_72==KW_REWRITE||(LA13_72 >= KW_ROLE && LA13_72 <= KW_ROLES)||(LA13_72 >= KW_SCHEMA && LA13_72 <= KW_SECOND)||(LA13_72 >= KW_SEMI && LA13_72 <= KW_SERVER)||(LA13_72 >= KW_SETS && LA13_72 <= KW_SKEWED)||(LA13_72 >= KW_SNAPSHOT && LA13_72 <= KW_SSL)||(LA13_72 >= KW_STATISTICS && LA13_72 <= KW_SUMMARY)||LA13_72==KW_TABLES||(LA13_72 >= KW_TBLPROPERTIES && LA13_72 <= KW_TERMINATED)||LA13_72==KW_TINYINT||(LA13_72 >= KW_TOUCH && LA13_72 <= KW_TRANSACTIONS)||LA13_72==KW_UNARCHIVE||LA13_72==KW_UNDO||LA13_72==KW_UNIONTYPE||(LA13_72 >= KW_UNLOCK && LA13_72 <= KW_UNSIGNED)||(LA13_72 >= KW_URI && LA13_72 <= KW_USE)||(LA13_72 >= KW_UTC && LA13_72 <= KW_VALIDATE)||LA13_72==KW_VALUE_TYPE||(LA13_72 >= KW_VECTORIZATION && LA13_72 <= KW_WEEK)||LA13_72==KW_WHILE||(LA13_72 >= KW_WORK && LA13_72 <= KW_YEAR)||LA13_72==KW_BATCH||LA13_72==KW_DAYOFWEEK||LA13_72==KW_HOLD_DDLTIME||LA13_72==KW_IGNORE||LA13_72==KW_NO_DROP||LA13_72==KW_OFFLINE||LA13_72==KW_PROTECTION||LA13_72==KW_READONLY) ) {s = 123;}
						 
						input.seek(index13_72);
						if ( s>=0 ) return s;
						break;

					case 3 : 
						int LA13_119 = input.LA(1);
						 
						int index13_119 = input.index();
						input.rewind();
						s = -1;
						if ( (synpred1_SelectClauseParser()) ) {s = 121;}
						else if ( (true) ) {s = 4;}
						 
						input.seek(index13_119);
						if ( s>=0 ) return s;
						break;

					case 4 : 
						int LA13_120 = input.LA(1);
						 
						int index13_120 = input.index();
						input.rewind();
						s = -1;
						if ( (synpred1_SelectClauseParser()) ) {s = 121;}
						else if ( (true) ) {s = 4;}
						 
						input.seek(index13_120);
						if ( s>=0 ) return s;
						break;

					case 5 : 
						int LA13_122 = input.LA(1);
						 
						int index13_122 = input.index();
						input.rewind();
						s = -1;
						if ( (synpred1_SelectClauseParser()) ) {s = 121;}
						else if ( (true) ) {s = 4;}
						 
						input.seek(index13_122);
						if ( s>=0 ) return s;
						break;

					case 6 : 
						int LA13_123 = input.LA(1);
						 
						int index13_123 = input.index();
						input.rewind();
						s = -1;
						if ( (synpred1_SelectClauseParser()) ) {s = 121;}
						else if ( (true) ) {s = 4;}
						 
						input.seek(index13_123);
						if ( s>=0 ) return s;
						break;
			}
			if (state.backtracking>0) {state.failed=true; return -1;}
			NoViableAltException nvae =
				new NoViableAltException(getDescription(), 13, _s, input);
			error(nvae);
			throw nvae;
		}
	}

	static final String DFA12_eotS =
		"\171\uffff";
	static final String DFA12_eofS =
		"\1\4\2\uffff\1\2\14\uffff\3\2\146\uffff";
	static final String DFA12_minS =
		"\1\11\1\30\1\uffff\1\11\14\uffff\3\11\146\uffff";
	static final String DFA12_maxS =
		"\2\u021d\1\uffff\1\u015b\14\uffff\3\u015b\146\uffff";
	static final String DFA12_acceptS =
		"\2\uffff\1\1\1\uffff\1\3\25\uffff\1\2\136\uffff";
	static final String DFA12_specialS =
		"\171\uffff}>";
	static final String[] DFA12_transitionS = {
			"\1\4\16\uffff\1\2\1\uffff\4\2\2\uffff\1\2\1\uffff\1\2\1\uffff\1\1\1\2"+
			"\1\uffff\2\2\5\uffff\2\2\1\uffff\2\2\2\uffff\1\2\1\uffff\1\3\3\2\1\uffff"+
			"\2\2\1\uffff\4\2\2\uffff\1\2\7\uffff\1\2\1\uffff\1\2\1\uffff\3\2\1\uffff"+
			"\2\2\1\uffff\3\2\1\uffff\4\2\1\uffff\1\20\1\uffff\1\2\1\uffff\2\2\1\uffff"+
			"\1\2\1\uffff\1\2\1\4\1\uffff\1\2\1\uffff\3\2\5\uffff\4\2\5\uffff\2\2"+
			"\1\4\2\uffff\1\2\1\uffff\1\4\1\uffff\1\4\2\2\3\uffff\2\2\1\uffff\3\2"+
			"\1\4\1\uffff\1\4\3\uffff\3\2\1\uffff\4\2\1\4\2\uffff\1\2\1\uffff\1\22"+
			"\2\2\1\uffff\5\2\1\uffff\1\4\3\2\1\uffff\1\2\1\4\2\2\1\uffff\1\2\1\uffff"+
			"\2\2\1\uffff\1\2\1\uffff\1\2\1\uffff\1\2\2\uffff\2\2\1\uffff\1\4\2\uffff"+
			"\2\2\1\uffff\2\2\2\uffff\2\2\1\uffff\1\2\3\uffff\1\2\1\uffff\1\2\1\uffff"+
			"\2\2\1\uffff\1\2\1\uffff\3\2\1\4\2\uffff\10\2\1\uffff\1\2\2\uffff\2\2"+
			"\4\uffff\3\2\1\4\4\2\1\uffff\5\2\1\uffff\1\2\1\21\2\2\1\uffff\7\2\1\uffff"+
			"\1\2\1\uffff\3\2\2\uffff\1\2\1\uffff\3\2\4\uffff\1\2\1\uffff\1\2\1\4"+
			"\1\2\1\uffff\3\2\1\uffff\2\2\2\uffff\3\2\1\uffff\1\2\1\uffff\5\2\1\uffff"+
			"\1\4\1\2\1\4\1\uffff\3\2\21\uffff\1\4\44\uffff\1\2\42\uffff\1\2\52\uffff"+
			"\1\2\3\uffff\1\2\52\uffff\1\2\3\uffff\1\2\26\uffff\1\2\4\uffff\1\2",
			"\1\2\1\uffff\4\2\2\uffff\1\2\1\uffff\1\2\2\uffff\1\2\1\uffff\2\2\5\uffff"+
			"\2\2\1\uffff\2\2\2\uffff\1\2\1\uffff\4\2\1\uffff\2\2\1\uffff\4\2\2\uffff"+
			"\1\2\7\uffff\1\2\1\uffff\1\2\1\uffff\3\2\1\uffff\2\2\1\uffff\3\2\1\uffff"+
			"\4\2\1\uffff\1\2\1\uffff\1\2\1\uffff\2\2\1\uffff\1\2\1\uffff\1\2\2\uffff"+
			"\1\2\1\uffff\3\2\5\uffff\4\2\5\uffff\2\2\3\uffff\1\2\4\uffff\2\2\3\uffff"+
			"\2\2\1\uffff\3\2\6\uffff\3\2\1\uffff\4\2\3\uffff\1\2\1\uffff\3\2\1\uffff"+
			"\5\2\2\uffff\3\2\1\uffff\1\2\1\uffff\2\2\1\uffff\1\2\1\uffff\2\2\1\uffff"+
			"\1\2\1\uffff\1\2\1\uffff\1\2\2\uffff\2\2\4\uffff\2\2\1\uffff\2\2\2\uffff"+
			"\2\2\1\uffff\1\2\3\uffff\1\2\1\uffff\1\2\1\uffff\2\2\1\uffff\1\2\1\uffff"+
			"\3\2\3\uffff\10\2\1\uffff\1\2\2\uffff\2\2\4\uffff\3\2\1\uffff\4\2\1\uffff"+
			"\5\2\1\uffff\4\2\1\uffff\7\2\1\uffff\1\2\1\uffff\3\2\2\uffff\1\2\1\uffff"+
			"\3\2\4\uffff\1\2\1\uffff\1\2\1\uffff\1\2\1\uffff\3\2\1\uffff\2\2\2\uffff"+
			"\3\2\1\uffff\1\2\1\uffff\5\2\2\uffff\1\2\2\uffff\3\2\4\uffff\1\32\61"+
			"\uffff\1\2\42\uffff\1\2\52\uffff\1\2\3\uffff\1\2\52\uffff\1\2\3\uffff"+
			"\1\2\26\uffff\1\2\4\uffff\1\2",
			"",
			"\1\2\46\uffff\1\4\6\uffff\1\2\51\uffff\1\2\11\uffff\1\2\26\uffff\1\2"+
			"\4\uffff\1\2\1\uffff\1\2\13\uffff\1\2\1\uffff\1\2\13\uffff\1\2\4\uffff"+
			"\1\2\11\uffff\1\2\5\uffff\1\2\22\uffff\1\2\34\uffff\1\2\27\uffff\1\2"+
			"\14\uffff\1\2\36\uffff\1\2\26\uffff\1\2\1\uffff\1\2\25\uffff\1\2",
			"",
			"",
			"",
			"",
			"",
			"",
			"",
			"",
			"",
			"",
			"",
			"",
			"\1\2\46\uffff\1\4\6\uffff\1\2\51\uffff\1\2\11\uffff\1\2\26\uffff\1\2"+
			"\4\uffff\1\2\1\uffff\1\2\13\uffff\1\2\1\uffff\1\2\13\uffff\1\2\4\uffff"+
			"\1\2\11\uffff\1\2\5\uffff\1\2\22\uffff\1\2\34\uffff\1\2\27\uffff\1\2"+
			"\14\uffff\1\2\36\uffff\1\2\26\uffff\1\2\1\uffff\1\2\25\uffff\1\2",
			"\1\2\46\uffff\1\4\6\uffff\1\2\51\uffff\1\2\11\uffff\1\2\26\uffff\1\2"+
			"\4\uffff\1\2\1\uffff\1\2\13\uffff\1\2\1\uffff\1\2\13\uffff\1\2\4\uffff"+
			"\1\2\11\uffff\1\2\5\uffff\1\2\22\uffff\1\2\34\uffff\1\2\27\uffff\1\2"+
			"\14\uffff\1\2\36\uffff\1\2\26\uffff\1\2\1\uffff\1\2\25\uffff\1\2",
			"\1\2\55\uffff\1\2\51\uffff\1\2\11\uffff\1\2\26\uffff\1\2\4\uffff\1\2"+
			"\1\uffff\1\2\13\uffff\1\2\1\uffff\1\2\13\uffff\1\2\4\uffff\1\2\11\uffff"+
			"\1\2\5\uffff\1\2\22\uffff\1\2\34\uffff\1\2\27\uffff\1\2\14\uffff\1\2"+
			"\36\uffff\1\2\26\uffff\1\2\1\uffff\1\2\16\uffff\1\4\6\uffff\1\2",
			"",
			"",
			"",
			"",
			"",
			"",
			"",
			"",
			"",
			"",
			"",
			"",
			"",
			"",
			"",
			"",
			"",
			"",
			"",
			"",
			"",
			"",
			"",
			"",
			"",
			"",
			"",
			"",
			"",
			"",
			"",
			"",
			"",
			"",
			"",
			"",
			"",
			"",
			"",
			"",
			"",
			"",
			"",
			"",
			"",
			"",
			"",
			"",
			"",
			"",
			"",
			"",
			"",
			"",
			"",
			"",
			"",
			"",
			"",
			"",
			"",
			"",
			"",
			"",
			"",
			"",
			"",
			"",
			"",
			"",
			"",
			"",
			"",
			"",
			"",
			"",
			"",
			"",
			"",
			"",
			"",
			"",
			"",
			"",
			"",
			"",
			"",
			"",
			"",
			"",
			"",
			"",
			"",
			"",
			"",
			"",
			"",
			"",
			"",
			"",
			"",
			""
	};

	static final short[] DFA12_eot = DFA.unpackEncodedString(DFA12_eotS);
	static final short[] DFA12_eof = DFA.unpackEncodedString(DFA12_eofS);
	static final char[] DFA12_min = DFA.unpackEncodedStringToUnsignedChars(DFA12_minS);
	static final char[] DFA12_max = DFA.unpackEncodedStringToUnsignedChars(DFA12_maxS);
	static final short[] DFA12_accept = DFA.unpackEncodedString(DFA12_acceptS);
	static final short[] DFA12_special = DFA.unpackEncodedString(DFA12_specialS);
	static final short[][] DFA12_transition;

	static {
		int numStates = DFA12_transitionS.length;
		DFA12_transition = new short[numStates][];
		for (int i=0; i<numStates; i++) {
			DFA12_transition[i] = DFA.unpackEncodedString(DFA12_transitionS[i]);
		}
	}

	protected class DFA12 extends DFA {

		public DFA12(BaseRecognizer recognizer) {
			this.recognizer = recognizer;
			this.decisionNumber = 12;
			this.eot = DFA12_eot;
			this.eof = DFA12_eof;
			this.min = DFA12_min;
			this.max = DFA12_max;
			this.accept = DFA12_accept;
			this.special = DFA12_special;
			this.transition = DFA12_transition;
		}
		@Override
		public String getDescription() {
			return "87:7: ( ( ( KW_AS )? identifier ) | ( KW_AS LPAREN identifier ( COMMA identifier )* RPAREN ) )?";
		}
	}

	static final String DFA16_eotS =
		"\u008b\uffff";
	static final String DFA16_eofS =
		"\1\uffff\2\3\u0088\uffff";
	static final String DFA16_minS =
		"\1\30\2\11\26\uffff\1\14\51\uffff\1\14\107\uffff";
	static final String DFA16_maxS =
		"\1\u021d\2\u015b\26\uffff\1\u021d\51\uffff\1\u021d\107\uffff";
	static final String DFA16_acceptS =
		"\3\uffff\1\1\27\uffff\1\2\157\uffff";
	static final String DFA16_specialS =
		"\u008b\uffff}>";
	static final String[] DFA16_transitionS = {
			"\1\1\1\uffff\4\2\2\uffff\1\2\1\uffff\1\2\2\uffff\1\2\1\uffff\2\2\5\uffff"+
			"\2\2\1\uffff\2\2\2\uffff\1\2\1\uffff\4\2\1\uffff\2\2\1\uffff\4\2\2\uffff"+
			"\1\2\7\uffff\1\2\1\uffff\1\2\1\uffff\3\2\1\uffff\2\2\1\uffff\3\2\1\uffff"+
			"\4\2\1\uffff\1\2\1\uffff\1\2\1\uffff\2\2\1\uffff\1\2\1\uffff\1\2\2\uffff"+
			"\1\2\1\uffff\3\2\5\uffff\4\2\5\uffff\2\2\3\uffff\1\2\4\uffff\2\2\3\uffff"+
			"\2\2\1\uffff\3\2\6\uffff\3\2\1\uffff\4\2\3\uffff\1\2\1\uffff\3\2\1\uffff"+
			"\5\2\2\uffff\3\2\1\uffff\1\2\1\uffff\2\2\1\uffff\1\2\1\uffff\2\2\1\uffff"+
			"\1\2\1\uffff\1\2\1\uffff\1\2\2\uffff\2\2\4\uffff\2\2\1\uffff\2\2\2\uffff"+
			"\2\2\1\uffff\1\2\3\uffff\1\2\1\uffff\1\2\1\uffff\2\2\1\uffff\1\2\1\uffff"+
			"\3\2\3\uffff\10\2\1\uffff\1\2\2\uffff\2\2\4\uffff\3\2\1\uffff\4\2\1\uffff"+
			"\5\2\1\uffff\4\2\1\uffff\7\2\1\uffff\1\2\1\uffff\3\2\2\uffff\1\2\1\uffff"+
			"\3\2\4\uffff\1\2\1\uffff\1\2\1\uffff\1\2\1\uffff\3\2\1\uffff\2\2\2\uffff"+
			"\3\2\1\uffff\1\2\1\uffff\5\2\2\uffff\1\2\2\uffff\3\2\66\uffff\1\2\42"+
			"\uffff\1\2\52\uffff\1\2\3\uffff\1\2\52\uffff\1\2\3\uffff\1\2\26\uffff"+
			"\1\2\4\uffff\1\2",
			"\1\3\31\uffff\1\33\6\uffff\3\33\11\uffff\1\33\1\3\30\uffff\2\33\2\uffff"+
			"\1\33\14\uffff\1\3\1\33\10\uffff\1\3\17\uffff\1\33\6\uffff\1\3\4\uffff"+
			"\1\3\1\uffff\1\3\13\uffff\1\3\1\33\1\3\13\uffff\1\3\4\uffff\1\3\11\uffff"+
			"\1\31\5\uffff\1\3\22\uffff\1\3\32\uffff\1\3\1\uffff\1\3\22\uffff\1\3"+
			"\4\uffff\1\3\12\uffff\1\33\1\uffff\1\3\7\uffff\2\33\10\uffff\2\33\13"+
			"\uffff\1\3\1\33\16\uffff\1\33\6\uffff\1\3\1\uffff\1\3\25\uffff\1\3",
			"\1\3\31\uffff\1\33\6\uffff\3\33\11\uffff\1\33\1\3\30\uffff\2\33\2\uffff"+
			"\1\33\14\uffff\1\3\1\33\10\uffff\1\3\17\uffff\1\33\6\uffff\1\3\4\uffff"+
			"\1\3\1\uffff\1\3\13\uffff\1\3\1\33\1\3\13\uffff\1\3\4\uffff\1\3\11\uffff"+
			"\1\103\5\uffff\1\3\22\uffff\1\3\32\uffff\1\3\1\uffff\1\3\22\uffff\1\3"+
			"\4\uffff\1\3\12\uffff\1\33\1\uffff\1\3\7\uffff\2\33\10\uffff\2\33\13"+
			"\uffff\1\3\1\33\16\uffff\1\33\6\uffff\1\3\1\uffff\1\3\25\uffff\1\3",
			"",
			"",
			"",
			"",
			"",
			"",
			"",
			"",
			"",
			"",
			"",
			"",
			"",
			"",
			"",
			"",
			"",
			"",
			"",
			"",
			"",
			"",
			"\1\3\13\uffff\6\3\2\uffff\1\3\1\uffff\2\3\1\uffff\1\3\1\uffff\2\3\1"+
			"\uffff\3\3\1\uffff\2\3\1\uffff\5\3\1\uffff\4\3\1\uffff\2\3\1\uffff\4"+
			"\3\2\uffff\1\3\4\uffff\2\3\1\uffff\1\3\1\uffff\5\3\1\uffff\2\3\1\uffff"+
			"\3\3\1\uffff\4\3\1\uffff\3\3\1\uffff\2\3\1\uffff\1\3\1\uffff\1\3\2\uffff"+
			"\5\3\2\uffff\2\3\1\uffff\6\3\3\uffff\2\3\3\uffff\1\3\2\uffff\1\3\1\uffff"+
			"\3\3\2\uffff\2\3\1\uffff\3\3\1\uffff\1\3\1\uffff\1\3\2\uffff\3\3\1\uffff"+
			"\4\3\3\uffff\1\3\1\uffff\3\3\1\uffff\5\3\1\uffff\4\3\1\uffff\1\3\1\uffff"+
			"\2\3\1\uffff\1\3\1\uffff\6\3\1\uffff\1\3\2\uffff\2\3\4\uffff\2\3\1\uffff"+
			"\2\3\2\uffff\2\3\1\uffff\1\3\3\uffff\1\3\1\uffff\1\3\1\uffff\2\3\1\uffff"+
			"\1\3\1\uffff\3\3\3\uffff\10\3\1\uffff\1\3\2\uffff\2\3\4\uffff\3\3\1\uffff"+
			"\4\3\1\uffff\12\3\1\uffff\7\3\1\uffff\1\3\1\uffff\3\3\1\uffff\2\3\1\uffff"+
			"\3\3\2\uffff\1\3\1\uffff\1\3\1\uffff\1\3\1\uffff\1\3\1\uffff\3\3\1\uffff"+
			"\2\3\2\uffff\3\3\1\uffff\1\3\1\uffff\5\3\2\uffff\1\3\2\uffff\3\3\1\uffff"+
			"\1\33\2\uffff\1\3\2\uffff\1\3\2\uffff\3\3\10\uffff\3\3\36\uffff\1\3\42"+
			"\uffff\1\3\52\uffff\1\3\3\uffff\1\3\52\uffff\1\3\3\uffff\1\3\26\uffff"+
			"\1\3\4\uffff\1\3",
			"",
			"",
			"",
			"",
			"",
			"",
			"",
			"",
			"",
			"",
			"",
			"",
			"",
			"",
			"",
			"",
			"",
			"",
			"",
			"",
			"",
			"",
			"",
			"",
			"",
			"",
			"",
			"",
			"",
			"",
			"",
			"",
			"",
			"",
			"",
			"",
			"",
			"",
			"",
			"",
			"",
			"\1\3\13\uffff\6\3\2\uffff\1\3\1\uffff\2\3\1\uffff\1\3\1\uffff\2\3\1"+
			"\uffff\3\3\1\uffff\2\3\1\uffff\5\3\1\uffff\4\3\1\uffff\2\3\1\uffff\4"+
			"\3\2\uffff\1\3\4\uffff\2\3\1\uffff\1\3\1\uffff\5\3\1\uffff\2\3\1\uffff"+
			"\3\3\1\uffff\4\3\1\uffff\3\3\1\uffff\2\3\1\uffff\1\3\1\uffff\1\3\2\uffff"+
			"\5\3\2\uffff\2\3\1\uffff\6\3\3\uffff\2\3\3\uffff\1\3\2\uffff\1\3\1\uffff"+
			"\3\3\2\uffff\2\3\1\uffff\3\3\1\uffff\1\3\1\uffff\1\3\2\uffff\3\3\1\uffff"+
			"\4\3\3\uffff\1\3\1\uffff\3\3\1\uffff\5\3\1\uffff\4\3\1\uffff\1\3\1\uffff"+
			"\2\3\1\uffff\1\3\1\uffff\6\3\1\uffff\1\3\2\uffff\2\3\4\uffff\2\3\1\uffff"+
			"\2\3\2\uffff\2\3\1\uffff\1\3\3\uffff\1\3\1\uffff\1\3\1\uffff\2\3\1\uffff"+
			"\1\3\1\uffff\3\3\3\uffff\10\3\1\uffff\1\3\2\uffff\2\3\4\uffff\3\3\1\uffff"+
			"\4\3\1\uffff\12\3\1\uffff\7\3\1\uffff\1\3\1\uffff\3\3\1\uffff\2\3\1\uffff"+
			"\3\3\2\uffff\1\3\1\uffff\1\3\1\uffff\1\3\1\uffff\1\3\1\uffff\3\3\1\uffff"+
			"\2\3\2\uffff\3\3\1\uffff\1\3\1\uffff\5\3\2\uffff\1\3\2\uffff\3\3\1\uffff"+
			"\1\33\2\uffff\1\3\2\uffff\1\3\2\uffff\3\3\10\uffff\3\3\36\uffff\1\3\42"+
			"\uffff\1\3\52\uffff\1\3\3\uffff\1\3\52\uffff\1\3\3\uffff\1\3\26\uffff"+
			"\1\3\4\uffff\1\3",
			"",
			"",
			"",
			"",
			"",
			"",
			"",
			"",
			"",
			"",
			"",
			"",
			"",
			"",
			"",
			"",
			"",
			"",
			"",
			"",
			"",
			"",
			"",
			"",
			"",
			"",
			"",
			"",
			"",
			"",
			"",
			"",
			"",
			"",
			"",
			"",
			"",
			"",
			"",
			"",
			"",
			"",
			"",
			"",
			"",
			"",
			"",
			"",
			"",
			"",
			"",
			"",
			"",
			"",
			"",
			"",
			"",
			"",
			"",
			"",
			"",
			"",
			"",
			"",
			"",
			"",
			"",
			"",
			"",
			"",
			""
	};

	static final short[] DFA16_eot = DFA.unpackEncodedString(DFA16_eotS);
	static final short[] DFA16_eof = DFA.unpackEncodedString(DFA16_eofS);
	static final char[] DFA16_min = DFA.unpackEncodedStringToUnsignedChars(DFA16_minS);
	static final char[] DFA16_max = DFA.unpackEncodedStringToUnsignedChars(DFA16_maxS);
	static final short[] DFA16_accept = DFA.unpackEncodedString(DFA16_acceptS);
	static final short[] DFA16_special = DFA.unpackEncodedString(DFA16_specialS);
	static final short[][] DFA16_transition;

	static {
		int numStates = DFA16_transitionS.length;
		DFA16_transition = new short[numStates][];
		for (int i=0; i<numStates; i++) {
			DFA16_transition[i] = DFA.unpackEncodedString(DFA16_transitionS[i]);
		}
	}

	protected class DFA16 extends DFA {

		public DFA16(BaseRecognizer recognizer) {
			this.recognizer = recognizer;
			this.decisionNumber = 16;
			this.eot = DFA16_eot;
			this.eof = DFA16_eof;
			this.min = DFA16_min;
			this.max = DFA16_max;
			this.accept = DFA16_accept;
			this.special = DFA16_special;
			this.transition = DFA16_transition;
		}
		@Override
		public String getDescription() {
			return "99:65: ( aliasList | columnNameTypeList )";
		}
	}

	static final String DFA19_eotS =
		"\126\uffff";
	static final String DFA19_eofS =
		"\126\uffff";
	static final String DFA19_minS =
		"\1\14\1\uffff\2\4\26\uffff\1\30\32\uffff\1\30\33\uffff\2\0\1\uffff\2\0";
	static final String DFA19_maxS =
		"\1\u021d\1\uffff\2\u015f\26\uffff\1\u021d\32\uffff\1\u021d\33\uffff\2"+
		"\0\1\uffff\2\0";
	static final String DFA19_acceptS =
		"\1\uffff\1\1\2\uffff\1\2\113\uffff\1\1\2\uffff\1\1\2\uffff";
	static final String DFA19_specialS =
		"\1\0\31\uffff\1\1\32\uffff\1\2\33\uffff\1\3\1\4\1\uffff\1\5\1\6}>";
	static final String[] DFA19_transitionS = {
			"\1\4\13\uffff\1\2\1\4\4\3\2\uffff\1\3\1\uffff\1\3\1\4\1\uffff\1\3\1\uffff"+
			"\2\3\1\uffff\3\4\1\uffff\2\3\1\uffff\2\3\2\4\1\3\1\uffff\4\3\1\uffff"+
			"\2\3\1\uffff\4\3\2\uffff\1\3\4\uffff\2\4\1\uffff\1\3\1\uffff\1\3\1\4"+
			"\3\3\1\uffff\2\3\1\uffff\3\3\1\uffff\4\3\1\uffff\1\3\1\4\1\3\1\uffff"+
			"\2\3\1\uffff\1\3\1\uffff\1\3\2\uffff\1\3\1\4\3\3\2\uffff\2\4\1\uffff"+
			"\4\3\2\4\3\uffff\2\3\3\uffff\1\3\2\uffff\1\4\1\uffff\2\3\1\4\2\uffff"+
			"\2\3\1\uffff\3\3\1\uffff\1\4\1\uffff\1\4\2\uffff\3\3\1\uffff\4\3\3\uffff"+
			"\1\3\1\uffff\3\3\1\uffff\5\3\1\uffff\1\4\3\3\1\uffff\1\3\1\uffff\2\3"+
			"\1\uffff\1\3\1\uffff\2\3\1\4\1\3\1\4\1\3\1\uffff\1\3\2\uffff\2\3\4\uffff"+
			"\2\3\1\uffff\2\3\2\uffff\2\3\1\uffff\1\3\3\uffff\1\3\1\uffff\1\3\1\uffff"+
			"\2\3\1\uffff\1\3\1\uffff\3\3\3\uffff\10\3\1\uffff\1\3\2\uffff\2\3\4\uffff"+
			"\3\3\1\uffff\4\3\1\uffff\5\3\1\4\4\3\1\uffff\7\3\1\uffff\1\3\1\uffff"+
			"\3\3\1\uffff\1\4\1\3\1\uffff\3\3\2\uffff\1\4\1\uffff\1\3\1\uffff\1\3"+
			"\1\uffff\1\3\1\uffff\3\3\1\uffff\2\3\2\uffff\3\3\1\uffff\1\3\1\uffff"+
			"\5\3\2\uffff\1\3\2\uffff\3\3\4\uffff\1\4\2\uffff\1\4\2\uffff\3\4\10\uffff"+
			"\1\1\2\4\36\uffff\1\3\42\uffff\1\3\52\uffff\1\3\3\uffff\1\3\52\uffff"+
			"\1\3\3\uffff\1\3\26\uffff\1\3\4\uffff\1\3",
			"",
			"\3\4\2\uffff\2\4\2\uffff\2\4\1\uffff\1\32\1\uffff\2\4\1\uffff\2\4\12"+
			"\uffff\1\4\7\uffff\1\4\144\uffff\1\4\13\uffff\1\4\14\uffff\1\4\30\uffff"+
			"\1\4\11\uffff\1\4\34\uffff\1\4\2\uffff\1\4\13\uffff\1\4\4\uffff\1\4\72"+
			"\uffff\1\4\24\uffff\2\4\1\uffff\2\4\1\uffff\3\4\2\uffff\1\4\4\uffff\1"+
			"\4\3\uffff\1\4",
			"\3\4\2\uffff\2\4\2\uffff\2\4\1\uffff\1\65\1\uffff\2\4\1\uffff\2\4\12"+
			"\uffff\1\4\7\uffff\1\4\144\uffff\1\4\13\uffff\1\4\14\uffff\1\4\30\uffff"+
			"\1\4\11\uffff\1\4\34\uffff\1\4\2\uffff\1\4\13\uffff\1\4\4\uffff\1\4\72"+
			"\uffff\1\4\24\uffff\2\4\1\uffff\2\4\1\uffff\3\4\2\uffff\1\4\4\uffff\1"+
			"\4\3\uffff\1\4",
			"",
			"",
			"",
			"",
			"",
			"",
			"",
			"",
			"",
			"",
			"",
			"",
			"",
			"",
			"",
			"",
			"",
			"",
			"",
			"",
			"",
			"",
			"\1\121\1\uffff\4\122\2\uffff\1\122\1\uffff\1\122\2\uffff\1\122\1\uffff"+
			"\2\122\5\uffff\2\122\1\uffff\2\122\2\uffff\1\122\1\uffff\4\122\1\uffff"+
			"\2\122\1\uffff\4\122\2\uffff\1\122\7\uffff\1\122\1\uffff\1\122\1\uffff"+
			"\3\122\1\uffff\2\122\1\uffff\3\122\1\uffff\4\122\1\uffff\1\122\1\uffff"+
			"\1\122\1\uffff\2\122\1\uffff\1\122\1\uffff\1\122\2\uffff\1\122\1\uffff"+
			"\3\122\5\uffff\4\122\5\uffff\2\122\3\uffff\1\122\4\uffff\2\122\3\uffff"+
			"\2\122\1\uffff\3\122\6\uffff\3\122\1\uffff\4\122\3\uffff\1\122\1\uffff"+
			"\3\122\1\uffff\5\122\2\uffff\3\122\1\uffff\1\122\1\uffff\2\122\1\uffff"+
			"\1\122\1\uffff\2\122\1\uffff\1\122\1\uffff\1\122\1\uffff\1\122\2\uffff"+
			"\2\122\4\uffff\2\122\1\uffff\2\122\2\uffff\2\122\1\uffff\1\122\3\uffff"+
			"\1\122\1\uffff\1\122\1\uffff\2\122\1\uffff\1\122\1\uffff\3\122\3\uffff"+
			"\10\122\1\uffff\1\122\2\uffff\2\122\4\uffff\3\122\1\uffff\4\122\1\uffff"+
			"\5\122\1\uffff\4\122\1\uffff\7\122\1\uffff\1\122\1\uffff\3\122\2\uffff"+
			"\1\122\1\uffff\3\122\4\uffff\1\122\1\uffff\1\122\1\uffff\1\122\1\uffff"+
			"\3\122\1\uffff\2\122\2\uffff\3\122\1\uffff\1\122\1\uffff\5\122\2\uffff"+
			"\1\122\2\uffff\3\122\25\uffff\1\120\40\uffff\1\122\42\uffff\1\122\52"+
			"\uffff\1\122\3\uffff\1\122\52\uffff\1\122\3\uffff\1\122\26\uffff\1\122"+
			"\4\uffff\1\122",
			"",
			"",
			"",
			"",
			"",
			"",
			"",
			"",
			"",
			"",
			"",
			"",
			"",
			"",
			"",
			"",
			"",
			"",
			"",
			"",
			"",
			"",
			"",
			"",
			"",
			"",
			"\1\124\1\uffff\4\125\2\uffff\1\125\1\uffff\1\125\2\uffff\1\125\1\uffff"+
			"\2\125\5\uffff\2\125\1\uffff\2\125\2\uffff\1\125\1\uffff\4\125\1\uffff"+
			"\2\125\1\uffff\4\125\2\uffff\1\125\7\uffff\1\125\1\uffff\1\125\1\uffff"+
			"\3\125\1\uffff\2\125\1\uffff\3\125\1\uffff\4\125\1\uffff\1\125\1\uffff"+
			"\1\125\1\uffff\2\125\1\uffff\1\125\1\uffff\1\125\2\uffff\1\125\1\uffff"+
			"\3\125\5\uffff\4\125\5\uffff\2\125\3\uffff\1\125\4\uffff\2\125\3\uffff"+
			"\2\125\1\uffff\3\125\6\uffff\3\125\1\uffff\4\125\3\uffff\1\125\1\uffff"+
			"\3\125\1\uffff\5\125\2\uffff\3\125\1\uffff\1\125\1\uffff\2\125\1\uffff"+
			"\1\125\1\uffff\2\125\1\uffff\1\125\1\uffff\1\125\1\uffff\1\125\2\uffff"+
			"\2\125\4\uffff\2\125\1\uffff\2\125\2\uffff\2\125\1\uffff\1\125\3\uffff"+
			"\1\125\1\uffff\1\125\1\uffff\2\125\1\uffff\1\125\1\uffff\3\125\3\uffff"+
			"\10\125\1\uffff\1\125\2\uffff\2\125\4\uffff\3\125\1\uffff\4\125\1\uffff"+
			"\5\125\1\uffff\4\125\1\uffff\7\125\1\uffff\1\125\1\uffff\3\125\2\uffff"+
			"\1\125\1\uffff\3\125\4\uffff\1\125\1\uffff\1\125\1\uffff\1\125\1\uffff"+
			"\3\125\1\uffff\2\125\2\uffff\3\125\1\uffff\1\125\1\uffff\5\125\2\uffff"+
			"\1\125\2\uffff\3\125\25\uffff\1\123\40\uffff\1\125\42\uffff\1\125\52"+
			"\uffff\1\125\3\uffff\1\125\52\uffff\1\125\3\uffff\1\125\26\uffff\1\125"+
			"\4\uffff\1\125",
			"",
			"",
			"",
			"",
			"",
			"",
			"",
			"",
			"",
			"",
			"",
			"",
			"",
			"",
			"",
			"",
			"",
			"",
			"",
			"",
			"",
			"",
			"",
			"",
			"",
			"",
			"",
			"\1\uffff",
			"\1\uffff",
			"",
			"\1\uffff",
			"\1\uffff"
	};

	static final short[] DFA19_eot = DFA.unpackEncodedString(DFA19_eotS);
	static final short[] DFA19_eof = DFA.unpackEncodedString(DFA19_eofS);
	static final char[] DFA19_min = DFA.unpackEncodedStringToUnsignedChars(DFA19_minS);
	static final char[] DFA19_max = DFA.unpackEncodedStringToUnsignedChars(DFA19_maxS);
	static final short[] DFA19_accept = DFA.unpackEncodedString(DFA19_acceptS);
	static final short[] DFA19_special = DFA.unpackEncodedString(DFA19_specialS);
	static final short[][] DFA19_transition;

	static {
		int numStates = DFA19_transitionS.length;
		DFA19_transition = new short[numStates][];
		for (int i=0; i<numStates; i++) {
			DFA19_transition[i] = DFA.unpackEncodedString(DFA19_transitionS[i]);
		}
	}

	protected class DFA19 extends DFA {

		public DFA19(BaseRecognizer recognizer) {
			this.recognizer = recognizer;
			this.decisionNumber = 19;
			this.eot = DFA19_eot;
			this.eof = DFA19_eof;
			this.min = DFA19_min;
			this.max = DFA19_max;
			this.accept = DFA19_accept;
			this.special = DFA19_special;
			this.transition = DFA19_transition;
		}
		@Override
		public String getDescription() {
			return "104:1: selectExpression : ( ( tableAllColumns )=> tableAllColumns | expression );";
		}
		@Override
		public int specialStateTransition(int s, IntStream _input) throws NoViableAltException {
			TokenStream input = (TokenStream)_input;
			int _s = s;
			switch ( s ) {
					case 0 : 
						int LA19_0 = input.LA(1);
						 
						int index19_0 = input.index();
						input.rewind();
						s = -1;
						if ( (LA19_0==STAR) && (synpred2_SelectClauseParser())) {s = 1;}
						else if ( (LA19_0==Identifier) ) {s = 2;}
						else if ( ((LA19_0 >= KW_ABORT && LA19_0 <= KW_AFTER)||LA19_0==KW_ANALYZE||LA19_0==KW_ARCHIVE||LA19_0==KW_ASC||(LA19_0 >= KW_AUTOCOMMIT && LA19_0 <= KW_BEFORE)||(LA19_0 >= KW_BUCKET && LA19_0 <= KW_BUCKETS)||(LA19_0 >= KW_CACHE && LA19_0 <= KW_CASCADE)||LA19_0==KW_CHANGE||(LA19_0 >= KW_CLUSTER && LA19_0 <= KW_COLLECTION)||(LA19_0 >= KW_COLUMNS && LA19_0 <= KW_COMMENT)||(LA19_0 >= KW_COMPACT && LA19_0 <= KW_CONCATENATE)||LA19_0==KW_CONTINUE||LA19_0==KW_DATA||LA19_0==KW_DATABASES||(LA19_0 >= KW_DATETIME && LA19_0 <= KW_DBPROPERTIES)||(LA19_0 >= KW_DEFERRED && LA19_0 <= KW_DEFINED)||(LA19_0 >= KW_DELIMITED && LA19_0 <= KW_DESC)||(LA19_0 >= KW_DETAIL && LA19_0 <= KW_DISABLE)||LA19_0==KW_DISTRIBUTE||LA19_0==KW_DOW||(LA19_0 >= KW_DUMP && LA19_0 <= KW_ELEM_TYPE)||LA19_0==KW_ENABLE||LA19_0==KW_ESCAPED||LA19_0==KW_EXCLUSIVE||(LA19_0 >= KW_EXPLAIN && LA19_0 <= KW_EXPRESSION)||(LA19_0 >= KW_FIELDS && LA19_0 <= KW_FIRST)||(LA19_0 >= KW_FORMAT && LA19_0 <= KW_FORMATTED)||LA19_0==KW_FUNCTIONS||(LA19_0 >= KW_HOUR && LA19_0 <= KW_IDXPROPERTIES)||(LA19_0 >= KW_INDEX && LA19_0 <= KW_INDEXES)||(LA19_0 >= KW_INPATH && LA19_0 <= KW_INPUTFORMAT)||(LA19_0 >= KW_ISOLATION && LA19_0 <= KW_JAR)||(LA19_0 >= KW_KEY && LA19_0 <= KW_LAST)||LA19_0==KW_LEVEL||(LA19_0 >= KW_LIMIT && LA19_0 <= KW_LOAD)||(LA19_0 >= KW_LOCATION && LA19_0 <= KW_LONG)||(LA19_0 >= KW_MAPJOIN && LA19_0 <= KW_MATERIALIZED)||LA19_0==KW_METADATA||(LA19_0 >= KW_MINUTE && LA19_0 <= KW_MONTH)||LA19_0==KW_MSCK||(LA19_0 >= KW_NORELY && LA19_0 <= KW_NOSCAN)||LA19_0==KW_NOVALIDATE||LA19_0==KW_NULLS||LA19_0==KW_OFFSET||(LA19_0 >= KW_OPERATOR && LA19_0 <= KW_OPTION)||(LA19_0 >= KW_OUTPUTDRIVER && LA19_0 <= KW_OUTPUTFORMAT)||(LA19_0 >= KW_OVERWRITE && LA19_0 <= KW_OWNER)||(LA19_0 >= KW_PARTITIONED && LA19_0 <= KW_PARTITIONS)||LA19_0==KW_PLUS||LA19_0==KW_PRETTY||LA19_0==KW_PRINCIPALS||(LA19_0 >= KW_PURGE && LA19_0 <= KW_QUARTER)||LA19_0==KW_READ||(LA19_0 >= KW_REBUILD && LA19_0 <= KW_RECORDWRITER)||(LA19_0 >= KW_RELOAD && LA19_0 <= KW_RESTRICT)||LA19_0==KW_REWRITE||(LA19_0 >= KW_ROLE && LA19_0 <= KW_ROLES)||(LA19_0 >= KW_SCHEMA && LA19_0 <= KW_SECOND)||(LA19_0 >= KW_SEMI && LA19_0 <= KW_SERVER)||(LA19_0 >= KW_SETS && LA19_0 <= KW_SKEWED)||(LA19_0 >= KW_SNAPSHOT && LA19_0 <= KW_SSL)||(LA19_0 >= KW_STATISTICS && LA19_0 <= KW_SUMMARY)||LA19_0==KW_TABLES||(LA19_0 >= KW_TBLPROPERTIES && LA19_0 <= KW_TERMINATED)||LA19_0==KW_TINYINT||(LA19_0 >= KW_TOUCH && LA19_0 <= KW_TRANSACTIONS)||LA19_0==KW_UNARCHIVE||LA19_0==KW_UNDO||LA19_0==KW_UNIONTYPE||(LA19_0 >= KW_UNLOCK && LA19_0 <= KW_UNSIGNED)||(LA19_0 >= KW_URI && LA19_0 <= KW_USE)||(LA19_0 >= KW_UTC && LA19_0 <= KW_VALIDATE)||LA19_0==KW_VALUE_TYPE||(LA19_0 >= KW_VECTORIZATION && LA19_0 <= KW_WEEK)||LA19_0==KW_WHILE||(LA19_0 >= KW_WORK && LA19_0 <= KW_YEAR)||LA19_0==KW_BATCH||LA19_0==KW_DAYOFWEEK||LA19_0==KW_HOLD_DDLTIME||LA19_0==KW_IGNORE||LA19_0==KW_NO_DROP||LA19_0==KW_OFFLINE||LA19_0==KW_PROTECTION||LA19_0==KW_READONLY) ) {s = 3;}
						else if ( (LA19_0==CharSetName||LA19_0==IntegralLiteral||LA19_0==KW_ARRAY||(LA19_0 >= KW_BIGINT && LA19_0 <= KW_BOOLEAN)||(LA19_0 >= KW_CASE && LA19_0 <= KW_CAST)||(LA19_0 >= KW_CURRENT_DATE && LA19_0 <= KW_CURRENT_TIMESTAMP)||LA19_0==KW_DATE||LA19_0==KW_DOUBLE||LA19_0==KW_EXISTS||(LA19_0 >= KW_EXTRACT && LA19_0 <= KW_FALSE)||(LA19_0 >= KW_FLOAT && LA19_0 <= KW_FLOOR)||LA19_0==KW_GROUPING||LA19_0==KW_IF||LA19_0==KW_INT||LA19_0==KW_INTERVAL||LA19_0==KW_MAP||LA19_0==KW_NOT||LA19_0==KW_NULL||LA19_0==KW_SMALLINT||LA19_0==KW_TIMESTAMP||LA19_0==KW_TRUE||LA19_0==LPAREN||LA19_0==MINUS||(LA19_0 >= Number && LA19_0 <= PLUS)||(LA19_0 >= StringLiteral && LA19_0 <= TILDE)) ) {s = 4;}
						 
						input.seek(index19_0);
						if ( s>=0 ) return s;
						break;

					case 1 : 
						int LA19_26 = input.LA(1);
						 
						int index19_26 = input.index();
						input.rewind();
						s = -1;
						if ( (LA19_26==STAR) && (synpred2_SelectClauseParser())) {s = 80;}
						else if ( (LA19_26==Identifier) ) {s = 81;}
						else if ( ((LA19_26 >= KW_ABORT && LA19_26 <= KW_AFTER)||LA19_26==KW_ANALYZE||LA19_26==KW_ARCHIVE||LA19_26==KW_ASC||(LA19_26 >= KW_AUTOCOMMIT && LA19_26 <= KW_BEFORE)||(LA19_26 >= KW_BUCKET && LA19_26 <= KW_BUCKETS)||(LA19_26 >= KW_CACHE && LA19_26 <= KW_CASCADE)||LA19_26==KW_CHANGE||(LA19_26 >= KW_CLUSTER && LA19_26 <= KW_COLLECTION)||(LA19_26 >= KW_COLUMNS && LA19_26 <= KW_COMMENT)||(LA19_26 >= KW_COMPACT && LA19_26 <= KW_CONCATENATE)||LA19_26==KW_CONTINUE||LA19_26==KW_DATA||LA19_26==KW_DATABASES||(LA19_26 >= KW_DATETIME && LA19_26 <= KW_DBPROPERTIES)||(LA19_26 >= KW_DEFERRED && LA19_26 <= KW_DEFINED)||(LA19_26 >= KW_DELIMITED && LA19_26 <= KW_DESC)||(LA19_26 >= KW_DETAIL && LA19_26 <= KW_DISABLE)||LA19_26==KW_DISTRIBUTE||LA19_26==KW_DOW||(LA19_26 >= KW_DUMP && LA19_26 <= KW_ELEM_TYPE)||LA19_26==KW_ENABLE||LA19_26==KW_ESCAPED||LA19_26==KW_EXCLUSIVE||(LA19_26 >= KW_EXPLAIN && LA19_26 <= KW_EXPRESSION)||(LA19_26 >= KW_FIELDS && LA19_26 <= KW_FIRST)||(LA19_26 >= KW_FORMAT && LA19_26 <= KW_FORMATTED)||LA19_26==KW_FUNCTIONS||(LA19_26 >= KW_HOUR && LA19_26 <= KW_IDXPROPERTIES)||(LA19_26 >= KW_INDEX && LA19_26 <= KW_INDEXES)||(LA19_26 >= KW_INPATH && LA19_26 <= KW_INPUTFORMAT)||(LA19_26 >= KW_ISOLATION && LA19_26 <= KW_JAR)||(LA19_26 >= KW_KEY && LA19_26 <= KW_LAST)||LA19_26==KW_LEVEL||(LA19_26 >= KW_LIMIT && LA19_26 <= KW_LOAD)||(LA19_26 >= KW_LOCATION && LA19_26 <= KW_LONG)||(LA19_26 >= KW_MAPJOIN && LA19_26 <= KW_MATERIALIZED)||LA19_26==KW_METADATA||(LA19_26 >= KW_MINUTE && LA19_26 <= KW_MONTH)||LA19_26==KW_MSCK||(LA19_26 >= KW_NORELY && LA19_26 <= KW_NOSCAN)||LA19_26==KW_NOVALIDATE||LA19_26==KW_NULLS||LA19_26==KW_OFFSET||(LA19_26 >= KW_OPERATOR && LA19_26 <= KW_OPTION)||(LA19_26 >= KW_OUTPUTDRIVER && LA19_26 <= KW_OUTPUTFORMAT)||(LA19_26 >= KW_OVERWRITE && LA19_26 <= KW_OWNER)||(LA19_26 >= KW_PARTITIONED && LA19_26 <= KW_PARTITIONS)||LA19_26==KW_PLUS||LA19_26==KW_PRETTY||LA19_26==KW_PRINCIPALS||(LA19_26 >= KW_PURGE && LA19_26 <= KW_QUARTER)||LA19_26==KW_READ||(LA19_26 >= KW_REBUILD && LA19_26 <= KW_RECORDWRITER)||(LA19_26 >= KW_RELOAD && LA19_26 <= KW_RESTRICT)||LA19_26==KW_REWRITE||(LA19_26 >= KW_ROLE && LA19_26 <= KW_ROLES)||(LA19_26 >= KW_SCHEMA && LA19_26 <= KW_SECOND)||(LA19_26 >= KW_SEMI && LA19_26 <= KW_SERVER)||(LA19_26 >= KW_SETS && LA19_26 <= KW_SKEWED)||(LA19_26 >= KW_SNAPSHOT && LA19_26 <= KW_SSL)||(LA19_26 >= KW_STATISTICS && LA19_26 <= KW_SUMMARY)||LA19_26==KW_TABLES||(LA19_26 >= KW_TBLPROPERTIES && LA19_26 <= KW_TERMINATED)||LA19_26==KW_TINYINT||(LA19_26 >= KW_TOUCH && LA19_26 <= KW_TRANSACTIONS)||LA19_26==KW_UNARCHIVE||LA19_26==KW_UNDO||LA19_26==KW_UNIONTYPE||(LA19_26 >= KW_UNLOCK && LA19_26 <= KW_UNSIGNED)||(LA19_26 >= KW_URI && LA19_26 <= KW_USE)||(LA19_26 >= KW_UTC && LA19_26 <= KW_VALIDATE)||LA19_26==KW_VALUE_TYPE||(LA19_26 >= KW_VECTORIZATION && LA19_26 <= KW_WEEK)||LA19_26==KW_WHILE||(LA19_26 >= KW_WORK && LA19_26 <= KW_YEAR)||LA19_26==KW_BATCH||LA19_26==KW_DAYOFWEEK||LA19_26==KW_HOLD_DDLTIME||LA19_26==KW_IGNORE||LA19_26==KW_NO_DROP||LA19_26==KW_OFFLINE||LA19_26==KW_PROTECTION||LA19_26==KW_READONLY) ) {s = 82;}
						 
						input.seek(index19_26);
						if ( s>=0 ) return s;
						break;

					case 2 : 
						int LA19_53 = input.LA(1);
						 
						int index19_53 = input.index();
						input.rewind();
						s = -1;
						if ( (LA19_53==STAR) && (synpred2_SelectClauseParser())) {s = 83;}
						else if ( (LA19_53==Identifier) ) {s = 84;}
						else if ( ((LA19_53 >= KW_ABORT && LA19_53 <= KW_AFTER)||LA19_53==KW_ANALYZE||LA19_53==KW_ARCHIVE||LA19_53==KW_ASC||(LA19_53 >= KW_AUTOCOMMIT && LA19_53 <= KW_BEFORE)||(LA19_53 >= KW_BUCKET && LA19_53 <= KW_BUCKETS)||(LA19_53 >= KW_CACHE && LA19_53 <= KW_CASCADE)||LA19_53==KW_CHANGE||(LA19_53 >= KW_CLUSTER && LA19_53 <= KW_COLLECTION)||(LA19_53 >= KW_COLUMNS && LA19_53 <= KW_COMMENT)||(LA19_53 >= KW_COMPACT && LA19_53 <= KW_CONCATENATE)||LA19_53==KW_CONTINUE||LA19_53==KW_DATA||LA19_53==KW_DATABASES||(LA19_53 >= KW_DATETIME && LA19_53 <= KW_DBPROPERTIES)||(LA19_53 >= KW_DEFERRED && LA19_53 <= KW_DEFINED)||(LA19_53 >= KW_DELIMITED && LA19_53 <= KW_DESC)||(LA19_53 >= KW_DETAIL && LA19_53 <= KW_DISABLE)||LA19_53==KW_DISTRIBUTE||LA19_53==KW_DOW||(LA19_53 >= KW_DUMP && LA19_53 <= KW_ELEM_TYPE)||LA19_53==KW_ENABLE||LA19_53==KW_ESCAPED||LA19_53==KW_EXCLUSIVE||(LA19_53 >= KW_EXPLAIN && LA19_53 <= KW_EXPRESSION)||(LA19_53 >= KW_FIELDS && LA19_53 <= KW_FIRST)||(LA19_53 >= KW_FORMAT && LA19_53 <= KW_FORMATTED)||LA19_53==KW_FUNCTIONS||(LA19_53 >= KW_HOUR && LA19_53 <= KW_IDXPROPERTIES)||(LA19_53 >= KW_INDEX && LA19_53 <= KW_INDEXES)||(LA19_53 >= KW_INPATH && LA19_53 <= KW_INPUTFORMAT)||(LA19_53 >= KW_ISOLATION && LA19_53 <= KW_JAR)||(LA19_53 >= KW_KEY && LA19_53 <= KW_LAST)||LA19_53==KW_LEVEL||(LA19_53 >= KW_LIMIT && LA19_53 <= KW_LOAD)||(LA19_53 >= KW_LOCATION && LA19_53 <= KW_LONG)||(LA19_53 >= KW_MAPJOIN && LA19_53 <= KW_MATERIALIZED)||LA19_53==KW_METADATA||(LA19_53 >= KW_MINUTE && LA19_53 <= KW_MONTH)||LA19_53==KW_MSCK||(LA19_53 >= KW_NORELY && LA19_53 <= KW_NOSCAN)||LA19_53==KW_NOVALIDATE||LA19_53==KW_NULLS||LA19_53==KW_OFFSET||(LA19_53 >= KW_OPERATOR && LA19_53 <= KW_OPTION)||(LA19_53 >= KW_OUTPUTDRIVER && LA19_53 <= KW_OUTPUTFORMAT)||(LA19_53 >= KW_OVERWRITE && LA19_53 <= KW_OWNER)||(LA19_53 >= KW_PARTITIONED && LA19_53 <= KW_PARTITIONS)||LA19_53==KW_PLUS||LA19_53==KW_PRETTY||LA19_53==KW_PRINCIPALS||(LA19_53 >= KW_PURGE && LA19_53 <= KW_QUARTER)||LA19_53==KW_READ||(LA19_53 >= KW_REBUILD && LA19_53 <= KW_RECORDWRITER)||(LA19_53 >= KW_RELOAD && LA19_53 <= KW_RESTRICT)||LA19_53==KW_REWRITE||(LA19_53 >= KW_ROLE && LA19_53 <= KW_ROLES)||(LA19_53 >= KW_SCHEMA && LA19_53 <= KW_SECOND)||(LA19_53 >= KW_SEMI && LA19_53 <= KW_SERVER)||(LA19_53 >= KW_SETS && LA19_53 <= KW_SKEWED)||(LA19_53 >= KW_SNAPSHOT && LA19_53 <= KW_SSL)||(LA19_53 >= KW_STATISTICS && LA19_53 <= KW_SUMMARY)||LA19_53==KW_TABLES||(LA19_53 >= KW_TBLPROPERTIES && LA19_53 <= KW_TERMINATED)||LA19_53==KW_TINYINT||(LA19_53 >= KW_TOUCH && LA19_53 <= KW_TRANSACTIONS)||LA19_53==KW_UNARCHIVE||LA19_53==KW_UNDO||LA19_53==KW_UNIONTYPE||(LA19_53 >= KW_UNLOCK && LA19_53 <= KW_UNSIGNED)||(LA19_53 >= KW_URI && LA19_53 <= KW_USE)||(LA19_53 >= KW_UTC && LA19_53 <= KW_VALIDATE)||LA19_53==KW_VALUE_TYPE||(LA19_53 >= KW_VECTORIZATION && LA19_53 <= KW_WEEK)||LA19_53==KW_WHILE||(LA19_53 >= KW_WORK && LA19_53 <= KW_YEAR)||LA19_53==KW_BATCH||LA19_53==KW_DAYOFWEEK||LA19_53==KW_HOLD_DDLTIME||LA19_53==KW_IGNORE||LA19_53==KW_NO_DROP||LA19_53==KW_OFFLINE||LA19_53==KW_PROTECTION||LA19_53==KW_READONLY) ) {s = 85;}
						 
						input.seek(index19_53);
						if ( s>=0 ) return s;
						break;

					case 3 : 
						int LA19_81 = input.LA(1);
						 
						int index19_81 = input.index();
						input.rewind();
						s = -1;
						if ( (synpred2_SelectClauseParser()) ) {s = 83;}
						else if ( (true) ) {s = 4;}
						 
						input.seek(index19_81);
						if ( s>=0 ) return s;
						break;

					case 4 : 
						int LA19_82 = input.LA(1);
						 
						int index19_82 = input.index();
						input.rewind();
						s = -1;
						if ( (synpred2_SelectClauseParser()) ) {s = 83;}
						else if ( (true) ) {s = 4;}
						 
						input.seek(index19_82);
						if ( s>=0 ) return s;
						break;

					case 5 : 
						int LA19_84 = input.LA(1);
						 
						int index19_84 = input.index();
						input.rewind();
						s = -1;
						if ( (synpred2_SelectClauseParser()) ) {s = 83;}
						else if ( (true) ) {s = 4;}
						 
						input.seek(index19_84);
						if ( s>=0 ) return s;
						break;

					case 6 : 
						int LA19_85 = input.LA(1);
						 
						int index19_85 = input.index();
						input.rewind();
						s = -1;
						if ( (synpred2_SelectClauseParser()) ) {s = 83;}
						else if ( (true) ) {s = 4;}
						 
						input.seek(index19_85);
						if ( s>=0 ) return s;
						break;
			}
			if (state.backtracking>0) {state.failed=true; return -1;}
			NoViableAltException nvae =
				new NoViableAltException(getDescription(), 19, _s, input);
			error(nvae);
			throw nvae;
		}
	}

	public static final BitSet FOLLOW_KW_SELECT_in_selectClause71 = new BitSet(new long[]{0xB7BEDDAD7F001000L,0x1FB3E56FF76FAC27L,0xD6BDF747B95D9D23L,0xE197F8EB5166C32FL,0xEB9BAABDBAFEFFDEL,0x0000000380F24393L,0x0000000800000001L,0x2000000000044000L,0x0000000021000002L});
	public static final BitSet FOLLOW_QUERY_HINT_in_selectClause73 = new BitSet(new long[]{0xB7BEDDAD7F001000L,0x1FB3E56FF76FAC27L,0xD6BDF747B95D9D23L,0xE197F8EB5166C32FL,0xEB9BAABDBAFEFFDEL,0x0000000380724393L,0x0000000800000001L,0x2000000000044000L,0x0000000021000002L});
	public static final BitSet FOLLOW_KW_ALL_in_selectClause79 = new BitSet(new long[]{0xB7BEDDAD3F001000L,0x1FB3E56EF76FAC27L,0xD6BDF747B95D9D23L,0xE197F8EB5166C32FL,0xEB9BAA9DBAFEFFDEL,0x0000000380724393L,0x0000000800000001L,0x2000000000044000L,0x0000000021000002L});
	public static final BitSet FOLLOW_KW_DISTINCT_in_selectClause85 = new BitSet(new long[]{0xB7BEDDAD3F001000L,0x1FB3E56EF76FAC27L,0xD6BDF747B95D9D23L,0xE197F8EB5166C32FL,0xEB9BAA9DBAFEFFDEL,0x0000000380724393L,0x0000000800000001L,0x2000000000044000L,0x0000000021000002L});
	public static final BitSet FOLLOW_selectList_in_selectClause89 = new BitSet(new long[]{0x0000000000000002L});
	public static final BitSet FOLLOW_KW_TRANSFORM_in_selectClause123 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000000L,0x0000000000000000L,0x0000000000000000L,0x0000000000000000L,0x0000000000004000L});
	public static final BitSet FOLLOW_selectTrfmClause_in_selectClause125 = new BitSet(new long[]{0x0000000000000002L});
	public static final BitSet FOLLOW_trfmClause_in_selectClause196 = new BitSet(new long[]{0x0000000000000002L});
	public static final BitSet FOLLOW_selectItem_in_selectList239 = new BitSet(new long[]{0x0000000000000202L});
	public static final BitSet FOLLOW_COMMA_in_selectList243 = new BitSet(new long[]{0xB7BEDDAD3F001000L,0x1FB3E56EF76FAC27L,0xD6BDF747B95D9D23L,0xE197F8EB5166C32FL,0xEB9BAA9DBAFEFFDEL,0x0000000380724393L,0x0000000800000001L,0x2000000000044000L,0x0000000021000002L});
	public static final BitSet FOLLOW_selectItem_in_selectList246 = new BitSet(new long[]{0x0000000000000202L});
	public static final BitSet FOLLOW_LPAREN_in_selectTrfmClause285 = new BitSet(new long[]{0xB7BEDDAD3F001000L,0x1FB3E56EF76FAC27L,0xD6BDF747B95D9D23L,0xE197F8EB5166C32FL,0xEB9BAA9DBAFEFFDEL,0x0000000380724393L,0x0000000800000001L,0x2000000000044000L,0x0000000021000002L});
	public static final BitSet FOLLOW_selectExpressionList_in_selectTrfmClause287 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000000L,0x0000000000000000L,0x0000000000000000L,0x0000000000000000L,0x0000000008000000L});
	public static final BitSet FOLLOW_RPAREN_in_selectTrfmClause289 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000000L,0x0000000000000000L,0x0800008000000000L,0x0040000000000000L});
	public static final BitSet FOLLOW_rowFormat_in_selectTrfmClause297 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000000L,0x0000000000000000L,0x0000008000000000L,0x0040000000000000L});
	public static final BitSet FOLLOW_recordWriter_in_selectTrfmClause301 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000000L,0x0000000000000000L,0x0000000000000000L,0x0040000000000000L});
	public static final BitSet FOLLOW_KW_USING_in_selectTrfmClause307 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000000L,0x0000000000000000L,0x0000000000000000L,0x0000000000000000L,0x0000000100000000L});
	public static final BitSet FOLLOW_StringLiteral_in_selectTrfmClause309 = new BitSet(new long[]{0x0000001000000000L,0x0000000000000000L,0x0000000000000000L,0x0800004000000000L});
	public static final BitSet FOLLOW_KW_AS_in_selectTrfmClause317 = new BitSet(new long[]{0xB7A6C1A53D000000L,0x0783A56AF76EA027L,0xD6B9F747B81D8C23L,0xE197F8EB5166C32AL,0xEB9BAA1D3AFEF7DEL,0x0000000000004393L,0x0000000800000001L,0x2000000000044000L,0x0000000021000002L});
	public static final BitSet FOLLOW_LPAREN_in_selectTrfmClause321 = new BitSet(new long[]{0xB7A6C1A53D000000L,0x0783A56AF76EA027L,0xD6B9F747B81D8C23L,0xE197F8EB5166C32AL,0xEB9BAA1D3AFEF7DEL,0x0000000000000393L,0x0000000800000001L,0x2000000000044000L,0x0000000021000002L});
	public static final BitSet FOLLOW_aliasList_in_selectTrfmClause324 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000000L,0x0000000000000000L,0x0000000000000000L,0x0000000000000000L,0x0000000008000000L});
	public static final BitSet FOLLOW_columnNameTypeList_in_selectTrfmClause328 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000000L,0x0000000000000000L,0x0000000000000000L,0x0000000000000000L,0x0000000008000000L});
	public static final BitSet FOLLOW_RPAREN_in_selectTrfmClause331 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000000L,0x0000000000000000L,0x0800004000000000L});
	public static final BitSet FOLLOW_aliasList_in_selectTrfmClause337 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000000L,0x0000000000000000L,0x0800004000000000L});
	public static final BitSet FOLLOW_columnNameTypeList_in_selectTrfmClause341 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000000L,0x0000000000000000L,0x0800004000000000L});
	public static final BitSet FOLLOW_rowFormat_in_selectTrfmClause353 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000000L,0x0000000000000000L,0x0000004000000000L});
	public static final BitSet FOLLOW_recordReader_in_selectTrfmClause357 = new BitSet(new long[]{0x0000000000000002L});
	public static final BitSet FOLLOW_tableAllColumns_in_selectItem426 = new BitSet(new long[]{0x0000000000000002L});
	public static final BitSet FOLLOW_expression_in_selectItem448 = new BitSet(new long[]{0xB7A6C1B53D000002L,0x0783A56AF76EA027L,0xD6B9F747B81D8C23L,0xE197F8EB5166C32AL,0xEB9BAA1D3AFEF7DEL,0x0000000000000393L,0x0000000800000001L,0x2000000000044000L,0x0000000021000002L});
	public static final BitSet FOLLOW_KW_AS_in_selectItem458 = new BitSet(new long[]{0xB7A6C1A53D000000L,0x0783A56AF76EA027L,0xD6B9F747B81D8C23L,0xE197F8EB5166C32AL,0xEB9BAA1D3AFEF7DEL,0x0000000000000393L,0x0000000800000001L,0x2000000000044000L,0x0000000021000002L});
	public static final BitSet FOLLOW_identifier_in_selectItem461 = new BitSet(new long[]{0x0000000000000002L});
	public static final BitSet FOLLOW_KW_AS_in_selectItem467 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000000L,0x0000000000000000L,0x0000000000000000L,0x0000000000000000L,0x0000000000004000L});
	public static final BitSet FOLLOW_LPAREN_in_selectItem469 = new BitSet(new long[]{0xB7A6C1A53D000000L,0x0783A56AF76EA027L,0xD6B9F747B81D8C23L,0xE197F8EB5166C32AL,0xEB9BAA1D3AFEF7DEL,0x0000000000000393L,0x0000000800000001L,0x2000000000044000L,0x0000000021000002L});
	public static final BitSet FOLLOW_identifier_in_selectItem471 = new BitSet(new long[]{0x0000000000000200L,0x0000000000000000L,0x0000000000000000L,0x0000000000000000L,0x0000000000000000L,0x0000000008000000L});
	public static final BitSet FOLLOW_COMMA_in_selectItem474 = new BitSet(new long[]{0xB7A6C1A53D000000L,0x0783A56AF76EA027L,0xD6B9F747B81D8C23L,0xE197F8EB5166C32AL,0xEB9BAA1D3AFEF7DEL,0x0000000000000393L,0x0000000800000001L,0x2000000000044000L,0x0000000021000002L});
	public static final BitSet FOLLOW_identifier_in_selectItem476 = new BitSet(new long[]{0x0000000000000200L,0x0000000000000000L,0x0000000000000000L,0x0000000000000000L,0x0000000000000000L,0x0000000008000000L});
	public static final BitSet FOLLOW_RPAREN_in_selectItem480 = new BitSet(new long[]{0x0000000000000002L});
	public static final BitSet FOLLOW_KW_MAP_in_trfmClause535 = new BitSet(new long[]{0xB7BEDDAD3F001000L,0x1FB3E56EF76FAC27L,0xD6BDF747B95D9D23L,0xE197F8EB5166C32FL,0xEB9BAA9DBAFEFFDEL,0x0000000380724393L,0x0000000800000001L,0x2000000000044000L,0x0000000021000002L});
	public static final BitSet FOLLOW_selectExpressionList_in_trfmClause540 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000000L,0x0000000000000000L,0x0800008000000000L,0x0040000000000000L});
	public static final BitSet FOLLOW_KW_REDUCE_in_trfmClause550 = new BitSet(new long[]{0xB7BEDDAD3F001000L,0x1FB3E56EF76FAC27L,0xD6BDF747B95D9D23L,0xE197F8EB5166C32FL,0xEB9BAA9DBAFEFFDEL,0x0000000380724393L,0x0000000800000001L,0x2000000000044000L,0x0000000021000002L});
	public static final BitSet FOLLOW_selectExpressionList_in_trfmClause552 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000000L,0x0000000000000000L,0x0800008000000000L,0x0040000000000000L});
	public static final BitSet FOLLOW_rowFormat_in_trfmClause562 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000000L,0x0000000000000000L,0x0000008000000000L,0x0040000000000000L});
	public static final BitSet FOLLOW_recordWriter_in_trfmClause566 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000000L,0x0000000000000000L,0x0000000000000000L,0x0040000000000000L});
	public static final BitSet FOLLOW_KW_USING_in_trfmClause572 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000000L,0x0000000000000000L,0x0000000000000000L,0x0000000000000000L,0x0000000100000000L});
	public static final BitSet FOLLOW_StringLiteral_in_trfmClause574 = new BitSet(new long[]{0x0000001000000000L,0x0000000000000000L,0x0000000000000000L,0x0800004000000000L});
	public static final BitSet FOLLOW_KW_AS_in_trfmClause582 = new BitSet(new long[]{0xB7A6C1A53D000000L,0x0783A56AF76EA027L,0xD6B9F747B81D8C23L,0xE197F8EB5166C32AL,0xEB9BAA1D3AFEF7DEL,0x0000000000004393L,0x0000000800000001L,0x2000000000044000L,0x0000000021000002L});
	public static final BitSet FOLLOW_LPAREN_in_trfmClause586 = new BitSet(new long[]{0xB7A6C1A53D000000L,0x0783A56AF76EA027L,0xD6B9F747B81D8C23L,0xE197F8EB5166C32AL,0xEB9BAA1D3AFEF7DEL,0x0000000000000393L,0x0000000800000001L,0x2000000000044000L,0x0000000021000002L});
	public static final BitSet FOLLOW_aliasList_in_trfmClause589 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000000L,0x0000000000000000L,0x0000000000000000L,0x0000000000000000L,0x0000000008000000L});
	public static final BitSet FOLLOW_columnNameTypeList_in_trfmClause593 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000000L,0x0000000000000000L,0x0000000000000000L,0x0000000000000000L,0x0000000008000000L});
	public static final BitSet FOLLOW_RPAREN_in_trfmClause596 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000000L,0x0000000000000000L,0x0800004000000000L});
	public static final BitSet FOLLOW_aliasList_in_trfmClause602 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000000L,0x0000000000000000L,0x0800004000000000L});
	public static final BitSet FOLLOW_columnNameTypeList_in_trfmClause606 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000000L,0x0000000000000000L,0x0800004000000000L});
	public static final BitSet FOLLOW_rowFormat_in_trfmClause618 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000000L,0x0000000000000000L,0x0000004000000000L});
	public static final BitSet FOLLOW_recordReader_in_trfmClause622 = new BitSet(new long[]{0x0000000000000002L});
	public static final BitSet FOLLOW_tableAllColumns_in_selectExpression691 = new BitSet(new long[]{0x0000000000000002L});
	public static final BitSet FOLLOW_expression_in_selectExpression703 = new BitSet(new long[]{0x0000000000000002L});
	public static final BitSet FOLLOW_selectExpression_in_selectExpressionList734 = new BitSet(new long[]{0x0000000000000202L});
	public static final BitSet FOLLOW_COMMA_in_selectExpressionList737 = new BitSet(new long[]{0xB7BEDDAD3F001000L,0x1FB3E56EF76FAC27L,0xD6BDF747B95D9D23L,0xE197F8EB5166C32FL,0xEB9BAA9DBAFEFFDEL,0x0000000380724393L,0x0000000800000001L,0x2000000000044000L,0x0000000021000002L});
	public static final BitSet FOLLOW_selectExpression_in_selectExpressionList739 = new BitSet(new long[]{0x0000000000000202L});
	public static final BitSet FOLLOW_KW_WINDOW_in_window_clause778 = new BitSet(new long[]{0xB7A6C1A53D000000L,0x0783A56AF76EA027L,0xD6B9F747B81D8C23L,0xE197F8EB5166C32AL,0xEB9BAA1D3AFEF7DEL,0x0000000000000393L,0x0000000800000001L,0x2000000000044000L,0x0000000021000002L});
	public static final BitSet FOLLOW_window_defn_in_window_clause780 = new BitSet(new long[]{0x0000000000000202L});
	public static final BitSet FOLLOW_COMMA_in_window_clause783 = new BitSet(new long[]{0xB7A6C1A53D000000L,0x0783A56AF76EA027L,0xD6B9F747B81D8C23L,0xE197F8EB5166C32AL,0xEB9BAA1D3AFEF7DEL,0x0000000000000393L,0x0000000800000001L,0x2000000000044000L,0x0000000021000002L});
	public static final BitSet FOLLOW_window_defn_in_window_clause785 = new BitSet(new long[]{0x0000000000000202L});
	public static final BitSet FOLLOW_identifier_in_window_defn821 = new BitSet(new long[]{0x0000001000000000L});
	public static final BitSet FOLLOW_KW_AS_in_window_defn823 = new BitSet(new long[]{0xB7A6C1A53D000000L,0x0783A56AF76EA027L,0xD6B9F747B81D8C23L,0xE197F8EB5166C32AL,0xEB9BAA1D3AFEF7DEL,0x0000000000004393L,0x0000000800000001L,0x2000000000044000L,0x0000000021000002L});
	public static final BitSet FOLLOW_window_specification_in_window_defn825 = new BitSet(new long[]{0x0000000000000002L});
	public static final BitSet FOLLOW_identifier_in_window_specification861 = new BitSet(new long[]{0x0000000000000002L});
	public static final BitSet FOLLOW_LPAREN_in_window_specification867 = new BitSet(new long[]{0xB7A6C1A53D000000L,0x0783A56AF76EA027L,0xD6B9F747B81D8C23L,0xF197F8EF5176CB2AL,0xEB9BAA1D3AFEF7DEL,0x0000000008000393L,0x0000000800000001L,0x2000000000044000L,0x0000000021000002L});
	public static final BitSet FOLLOW_identifier_in_window_specification869 = new BitSet(new long[]{0x0080000000000000L,0x0000000200000000L,0x0000000000000000L,0x1000000400100800L,0x0000000000002000L,0x0000000008000000L});
	public static final BitSet FOLLOW_partitioningSpec_in_window_specification872 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000000L,0x0000000000000000L,0x1000000400000000L,0x0000000000000000L,0x0000000008000000L});
	public static final BitSet FOLLOW_window_frame_in_window_specification875 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000000L,0x0000000000000000L,0x0000000000000000L,0x0000000000000000L,0x0000000008000000L});
	public static final BitSet FOLLOW_RPAREN_in_window_specification878 = new BitSet(new long[]{0x0000000000000002L});
	public static final BitSet FOLLOW_window_range_expression_in_window_frame905 = new BitSet(new long[]{0x0000000000000002L});
	public static final BitSet FOLLOW_window_value_expression_in_window_frame910 = new BitSet(new long[]{0x0000000000000002L});
	public static final BitSet FOLLOW_KW_ROWS_in_window_range_expression932 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000200L,0x0000000000000000L,0x0000000000000000L,0x0000040000000000L,0x0000000000100000L});
	public static final BitSet FOLLOW_window_frame_start_boundary_in_window_range_expression936 = new BitSet(new long[]{0x0000000000000002L});
	public static final BitSet FOLLOW_KW_ROWS_in_window_range_expression950 = new BitSet(new long[]{0x0000020000000000L});
	public static final BitSet FOLLOW_KW_BETWEEN_in_window_range_expression952 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000200L,0x0000000000000000L,0x0000000000000000L,0x0000040000000000L,0x0000000000100000L});
	public static final BitSet FOLLOW_window_frame_boundary_in_window_range_expression956 = new BitSet(new long[]{0x0000000200000000L});
	public static final BitSet FOLLOW_KW_AND_in_window_range_expression958 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000200L,0x0000000000000000L,0x0000000000000000L,0x0000040000000000L,0x0000000000100000L});
	public static final BitSet FOLLOW_window_frame_boundary_in_window_range_expression962 = new BitSet(new long[]{0x0000000000000002L});
	public static final BitSet FOLLOW_KW_RANGE_in_window_value_expression996 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000200L,0x0000000000000000L,0x0000000000000000L,0x0000040000000000L,0x0000000000100000L});
	public static final BitSet FOLLOW_window_frame_start_boundary_in_window_value_expression1000 = new BitSet(new long[]{0x0000000000000002L});
	public static final BitSet FOLLOW_KW_RANGE_in_window_value_expression1014 = new BitSet(new long[]{0x0000020000000000L});
	public static final BitSet FOLLOW_KW_BETWEEN_in_window_value_expression1016 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000200L,0x0000000000000000L,0x0000000000000000L,0x0000040000000000L,0x0000000000100000L});
	public static final BitSet FOLLOW_window_frame_boundary_in_window_value_expression1020 = new BitSet(new long[]{0x0000000200000000L});
	public static final BitSet FOLLOW_KW_AND_in_window_value_expression1022 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000200L,0x0000000000000000L,0x0000000000000000L,0x0000040000000000L,0x0000000000100000L});
	public static final BitSet FOLLOW_window_frame_boundary_in_window_value_expression1026 = new BitSet(new long[]{0x0000000000000002L});
	public static final BitSet FOLLOW_KW_UNBOUNDED_in_window_frame_start_boundary1061 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000000L,0x0000000000000000L,0x0000000002000000L});
	public static final BitSet FOLLOW_KW_PRECEDING_in_window_frame_start_boundary1063 = new BitSet(new long[]{0x0000000000000002L});
	public static final BitSet FOLLOW_KW_CURRENT_in_window_frame_start_boundary1079 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000000L,0x0000000000000000L,0x0800000000000000L});
	public static final BitSet FOLLOW_KW_ROW_in_window_frame_start_boundary1081 = new BitSet(new long[]{0x0000000000000002L});
	public static final BitSet FOLLOW_Number_in_window_frame_start_boundary1094 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000000L,0x0000000000000000L,0x0000000002000000L});
	public static final BitSet FOLLOW_KW_PRECEDING_in_window_frame_start_boundary1096 = new BitSet(new long[]{0x0000000000000002L});
	public static final BitSet FOLLOW_KW_UNBOUNDED_in_window_frame_boundary1127 = new BitSet(new long[]{0x0000000000000000L,0x2000000000000000L,0x0000000000000000L,0x0000000002000000L});
	public static final BitSet FOLLOW_KW_PRECEDING_in_window_frame_boundary1132 = new BitSet(new long[]{0x0000000000000002L});
	public static final BitSet FOLLOW_KW_FOLLOWING_in_window_frame_boundary1136 = new BitSet(new long[]{0x0000000000000002L});
	public static final BitSet FOLLOW_KW_CURRENT_in_window_frame_boundary1154 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000000L,0x0000000000000000L,0x0800000000000000L});
	public static final BitSet FOLLOW_KW_ROW_in_window_frame_boundary1156 = new BitSet(new long[]{0x0000000000000002L});
	public static final BitSet FOLLOW_Number_in_window_frame_boundary1169 = new BitSet(new long[]{0x0000000000000000L,0x2000000000000000L,0x0000000000000000L,0x0000000002000000L});
	public static final BitSet FOLLOW_KW_PRECEDING_in_window_frame_boundary1174 = new BitSet(new long[]{0x0000000000000002L});
	public static final BitSet FOLLOW_KW_FOLLOWING_in_window_frame_boundary1180 = new BitSet(new long[]{0x0000000000000002L});
	public static final BitSet FOLLOW_tableAllColumns_in_synpred1_SelectClauseParser421 = new BitSet(new long[]{0x0000000000000002L});
	public static final BitSet FOLLOW_tableAllColumns_in_synpred2_SelectClauseParser686 = new BitSet(new long[]{0x0000000000000002L});
}
