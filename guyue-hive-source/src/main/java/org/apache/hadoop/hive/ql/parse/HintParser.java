// $ANTLR 3.5.2 org/apache/hadoop/hive/ql/parse/HintParser.g 2017-11-09 09:02:31

package org.apache.hadoop.hive.ql.parse;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.hive.conf.HiveConf;


import org.antlr.runtime.*;
import java.util.Stack;
import java.util.List;
import java.util.ArrayList;

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
public class HintParser extends Parser {
	public static final String[] tokenNames = new String[] {
		"<invalid>", "<EOR>", "<DOWN>", "<UP>", "AMPERSAND", "BITWISEOR", "BITWISEXOR", 
		"ByteLengthLiteral", "COLON", "COMMA", "CONCATENATE", "CharSetLiteral", 
		"CharSetName", "DIV", "DIVIDE", "DOLLAR", "DOT", "Digit", "EQUAL", "EQUAL_NS", 
		"Exponent", "GREATERTHAN", "GREATERTHANOREQUALTO", "HexDigit", "Identifier", 
		"IntegralLiteral", "KW_ABORT", "KW_ADD", "KW_ADMIN", "KW_AFTER", "KW_ALL", 
		"KW_ALTER", "KW_ANALYZE", "KW_AND", "KW_ARCHIVE", "KW_ARRAY", "KW_AS", 
		"KW_ASC", "KW_AUTHORIZATION", "KW_AUTOCOMMIT", "KW_BEFORE", "KW_BETWEEN", 
		"KW_BIGINT", "KW_BINARY", "KW_BOOLEAN", "KW_BOTH", "KW_BUCKET", "KW_BUCKETS", 
		"KW_BY", "KW_CACHE", "KW_CASCADE", "KW_CASE", "KW_CAST", "KW_CHANGE", 
		"KW_CHAR", "KW_CLUSTER", "KW_CLUSTERED", "KW_CLUSTERSTATUS", "KW_COLLECTION", 
		"KW_COLUMN", "KW_COLUMNS", "KW_COMMENT", "KW_COMMIT", "KW_COMPACT", "KW_COMPACTIONS", 
		"KW_COMPUTE", "KW_CONCATENATE", "KW_CONF", "KW_CONSTRAINT", "KW_CONTINUE", 
		"KW_CREATE", "KW_CROSS", "KW_CUBE", "KW_CURRENT", "KW_CURRENT_DATE", "KW_CURRENT_TIMESTAMP", 
		"KW_CURSOR", "KW_DATA", "KW_DATABASE", "KW_DATABASES", "KW_DATE", "KW_DATETIME", 
		"KW_DAY", "KW_DBPROPERTIES", "KW_DECIMAL", "KW_DEFERRED", "KW_DEFINED", 
		"KW_DELETE", "KW_DELIMITED", "KW_DEPENDENCY", "KW_DESC", "KW_DESCRIBE", 
		"KW_DETAIL", "KW_DIRECTORIES", "KW_DIRECTORY", "KW_DISABLE", "KW_DISTINCT", 
		"KW_DISTRIBUTE", "KW_DOUBLE", "KW_DOW", "KW_DROP", "KW_DUMP", "KW_ELEM_TYPE", 
		"KW_ELSE", "KW_ENABLE", "KW_END", "KW_ESCAPED", "KW_EXCEPT", "KW_EXCHANGE", 
		"KW_EXCLUSIVE", "KW_EXISTS", "KW_EXPLAIN", "KW_EXPORT", "KW_EXPRESSION", 
		"KW_EXTENDED", "KW_EXTERNAL", "KW_EXTRACT", "KW_FALSE", "KW_FETCH", "KW_FIELDS", 
		"KW_FILE", "KW_FILEFORMAT", "KW_FIRST", "KW_FLOAT", "KW_FLOOR", "KW_FOLLOWING", 
		"KW_FOR", "KW_FOREIGN", "KW_FORMAT", "KW_FORMATTED", "KW_FROM", "KW_FULL", 
		"KW_FUNCTION", "KW_FUNCTIONS", "KW_GRANT", "KW_GROUP", "KW_GROUPING", 
		"KW_HAVING", "KW_HOUR", "KW_IDXPROPERTIES", "KW_IF", "KW_IMPORT", "KW_IN", 
		"KW_INDEX", "KW_INDEXES", "KW_INNER", "KW_INPATH", "KW_INPUTDRIVER", "KW_INPUTFORMAT", 
		"KW_INSERT", "KW_INT", "KW_INTERSECT", "KW_INTERVAL", "KW_INTO", "KW_IS", 
		"KW_ISOLATION", "KW_ITEMS", "KW_JAR", "KW_JOIN", "KW_KEY", "KW_KEYS", 
		"KW_KEY_TYPE", "KW_LAST", "KW_LATERAL", "KW_LEFT", "KW_LESS", "KW_LEVEL", 
		"KW_LIKE", "KW_LIMIT", "KW_LINES", "KW_LOAD", "KW_LOCAL", "KW_LOCATION", 
		"KW_LOCK", "KW_LOCKS", "KW_LOGICAL", "KW_LONG", "KW_MACRO", "KW_MAP", 
		"KW_MAPJOIN", "KW_MATCHED", "KW_MATERIALIZED", "KW_MERGE", "KW_METADATA", 
		"KW_MINUS", "KW_MINUTE", "KW_MONTH", "KW_MORE", "KW_MSCK", "KW_NONE", 
		"KW_NORELY", "KW_NOSCAN", "KW_NOT", "KW_NOVALIDATE", "KW_NULL", "KW_NULLS", 
		"KW_OF", "KW_OFFSET", "KW_ON", "KW_ONLY", "KW_OPERATOR", "KW_OPTION", 
		"KW_OR", "KW_ORDER", "KW_OUT", "KW_OUTER", "KW_OUTPUTDRIVER", "KW_OUTPUTFORMAT", 
		"KW_OVER", "KW_OVERWRITE", "KW_OWNER", "KW_PARTIALSCAN", "KW_PARTITION", 
		"KW_PARTITIONED", "KW_PARTITIONS", "KW_PERCENT", "KW_PLUS", "KW_PRECEDING", 
		"KW_PRECISION", "KW_PRESERVE", "KW_PRETTY", "KW_PRIMARY", "KW_PRINCIPALS", 
		"KW_PROCEDURE", "KW_PURGE", "KW_QUARTER", "KW_RANGE", "KW_READ", "KW_READS", 
		"KW_REBUILD", "KW_RECORDREADER", "KW_RECORDWRITER", "KW_REDUCE", "KW_REFERENCES", 
		"KW_REGEXP", "KW_RELOAD", "KW_RELY", "KW_RENAME", "KW_REPAIR", "KW_REPL", 
		"KW_REPLACE", "KW_REPLICATION", "KW_RESTRICT", "KW_REVOKE", "KW_REWRITE", 
		"KW_RIGHT", "KW_RLIKE", "KW_ROLE", "KW_ROLES", "KW_ROLLBACK", "KW_ROLLUP", 
		"KW_ROW", "KW_ROWS", "KW_SCHEMA", "KW_SCHEMAS", "KW_SECOND", "KW_SELECT", 
		"KW_SEMI", "KW_SERDE", "KW_SERDEPROPERTIES", "KW_SERVER", "KW_SET", "KW_SETS", 
		"KW_SHARED", "KW_SHOW", "KW_SHOW_DATABASE", "KW_SKEWED", "KW_SMALLINT", 
		"KW_SNAPSHOT", "KW_SORT", "KW_SORTED", "KW_SSL", "KW_START", "KW_STATISTICS", 
		"KW_STATUS", "KW_STORED", "KW_STREAMTABLE", "KW_STRING", "KW_STRUCT", 
		"KW_SUMMARY", "KW_TABLE", "KW_TABLES", "KW_TABLESAMPLE", "KW_TBLPROPERTIES", 
		"KW_TEMPORARY", "KW_TERMINATED", "KW_THEN", "KW_TIMESTAMP", "KW_TINYINT", 
		"KW_TO", "KW_TOUCH", "KW_TRANSACTION", "KW_TRANSACTIONS", "KW_TRANSFORM", 
		"KW_TRIGGER", "KW_TRUE", "KW_TRUNCATE", "KW_UNARCHIVE", "KW_UNBOUNDED", 
		"KW_UNDO", "KW_UNION", "KW_UNIONTYPE", "KW_UNIQUEJOIN", "KW_UNLOCK", "KW_UNSET", 
		"KW_UNSIGNED", "KW_UPDATE", "KW_URI", "KW_USE", "KW_USER", "KW_USING", 
		"KW_UTC", "KW_UTCTIMESTAMP", "KW_VALIDATE", "KW_VALUES", "KW_VALUE_TYPE", 
		"KW_VARCHAR", "KW_VECTORIZATION", "KW_VIEW", "KW_VIEWS", "KW_WAIT", "KW_WEEK", 
		"KW_WHEN", "KW_WHERE", "KW_WHILE", "KW_WINDOW", "KW_WITH", "KW_WORK", 
		"KW_WRITE", "KW_YEAR", "LCURLY", "LESSTHAN", "LESSTHANOREQUALTO", "LINE_COMMENT", 
		"LPAREN", "LSQUARE", "Letter", "MINUS", "MOD", "NOTEQUAL", "Number", "NumberLiteral", 
		"PLUS", "QUERY_HINT", "QUESTION", "QuotedIdentifier", "RCURLY", "RPAREN", 
		"RSQUARE", "RegexComponent", "SEMICOLON", "STAR", "StringLiteral", "TILDE", 
		"WS", "TOK_HINT", "TOK_HINTARGLIST", "TOK_HINTLIST", "TOK_MAPJOIN", "TOK_STREAMTABLE"
	};
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
	public static final int TOK_HINT=355;
	public static final int TOK_HINTARGLIST=356;
	public static final int TOK_HINTLIST=357;
	public static final int TOK_MAPJOIN=358;
	public static final int TOK_STREAMTABLE=359;

	// delegates
	public Parser[] getDelegates() {
		return new Parser[] {};
	}

	// delegators


	public HintParser(TokenStream input) {
		this(input, new RecognizerSharedState());
	}
	public HintParser(TokenStream input, RecognizerSharedState state) {
		super(input, state);
	}

	protected TreeAdaptor adaptor = new CommonTreeAdaptor();

	public void setTreeAdaptor(TreeAdaptor adaptor) {
		this.adaptor = adaptor;
	}
	public TreeAdaptor getTreeAdaptor() {
		return adaptor;
	}
	@Override public String[] getTokenNames() { return HintParser.tokenNames; }
	@Override public String getGrammarFileName() { return "org/apache/hadoop/hive/ql/parse/HintParser.g"; }


	  ArrayList<ParseError> errors = new ArrayList<ParseError>();

	  @Override
	  public void displayRecognitionError(String[] tokenNames,
	                                      RecognitionException e) {
	    errors.add(new ParseError(this, e, tokenNames));
	  }


	public static class hint_return extends ParserRuleReturnScope {
		ASTNode tree;
		@Override
		public ASTNode getTree() { return tree; }
	};


	// $ANTLR start "hint"
	// org/apache/hadoop/hive/ql/parse/HintParser.g:55:1: hint : hintList EOF -> ^( TOK_HINTLIST hintList ) ;
	public final HintParser.hint_return hint() throws RecognitionException {
		HintParser.hint_return retval = new HintParser.hint_return();
		retval.start = input.LT(1);

		ASTNode root_0 = null;

		Token EOF2=null;
		ParserRuleReturnScope hintList1 =null;

		ASTNode EOF2_tree=null;
		RewriteRuleTokenStream stream_EOF=new RewriteRuleTokenStream(adaptor,"token EOF");
		RewriteRuleSubtreeStream stream_hintList=new RewriteRuleSubtreeStream(adaptor,"rule hintList");

		try {
			// org/apache/hadoop/hive/ql/parse/HintParser.g:56:5: ( hintList EOF -> ^( TOK_HINTLIST hintList ) )
			// org/apache/hadoop/hive/ql/parse/HintParser.g:56:7: hintList EOF
			{
			pushFollow(FOLLOW_hintList_in_hint102);
			hintList1=hintList();
			state._fsp--;

			stream_hintList.add(hintList1.getTree());
			EOF2=(Token)match(input,EOF,FOLLOW_EOF_in_hint104);  
			stream_EOF.add(EOF2);

			// AST REWRITE
			// elements: hintList
			// token labels: 
			// rule labels: retval
			// token list labels: 
			// rule list labels: 
			// wildcard labels: 
			retval.tree = root_0;
			RewriteRuleSubtreeStream stream_retval=new RewriteRuleSubtreeStream(adaptor,"rule retval",retval!=null?retval.getTree():null);

			root_0 = (ASTNode)adaptor.nil();
			// 56:20: -> ^( TOK_HINTLIST hintList )
			{
				// org/apache/hadoop/hive/ql/parse/HintParser.g:56:23: ^( TOK_HINTLIST hintList )
				{
				ASTNode root_1 = (ASTNode)adaptor.nil();
				root_1 = (ASTNode)adaptor.becomeRoot((ASTNode)adaptor.create(TOK_HINTLIST, "TOK_HINTLIST"), root_1);
				adaptor.addChild(root_1, stream_hintList.nextTree());
				adaptor.addChild(root_0, root_1);
				}

			}


			retval.tree = root_0;

			}

			retval.stop = input.LT(-1);

			retval.tree = (ASTNode)adaptor.rulePostProcessing(root_0);
			adaptor.setTokenBoundaries(retval.tree, retval.start, retval.stop);

		}
		catch (RecognitionException re) {
			reportError(re);
			recover(input,re);
			retval.tree = (ASTNode)adaptor.errorNode(input, retval.start, input.LT(-1), re);
		}
		finally {
			// do for sure before leaving
		}
		return retval;
	}
	// $ANTLR end "hint"


	public static class hintList_return extends ParserRuleReturnScope {
		ASTNode tree;
		@Override
		public ASTNode getTree() { return tree; }
	};


	// $ANTLR start "hintList"
	// org/apache/hadoop/hive/ql/parse/HintParser.g:59:1: hintList : hintItem ( COMMA hintItem )* -> ( hintItem )+ ;
	public final HintParser.hintList_return hintList() throws RecognitionException {
		HintParser.hintList_return retval = new HintParser.hintList_return();
		retval.start = input.LT(1);

		ASTNode root_0 = null;

		Token COMMA4=null;
		ParserRuleReturnScope hintItem3 =null;
		ParserRuleReturnScope hintItem5 =null;

		ASTNode COMMA4_tree=null;
		RewriteRuleTokenStream stream_COMMA=new RewriteRuleTokenStream(adaptor,"token COMMA");
		RewriteRuleSubtreeStream stream_hintItem=new RewriteRuleSubtreeStream(adaptor,"rule hintItem");

		try {
			// org/apache/hadoop/hive/ql/parse/HintParser.g:60:5: ( hintItem ( COMMA hintItem )* -> ( hintItem )+ )
			// org/apache/hadoop/hive/ql/parse/HintParser.g:61:5: hintItem ( COMMA hintItem )*
			{
			pushFollow(FOLLOW_hintItem_in_hintList133);
			hintItem3=hintItem();
			state._fsp--;

			stream_hintItem.add(hintItem3.getTree());
			// org/apache/hadoop/hive/ql/parse/HintParser.g:61:14: ( COMMA hintItem )*
			loop1:
			while (true) {
				int alt1=2;
				int LA1_0 = input.LA(1);
				if ( (LA1_0==COMMA) ) {
					alt1=1;
				}

				switch (alt1) {
				case 1 :
					// org/apache/hadoop/hive/ql/parse/HintParser.g:61:15: COMMA hintItem
					{
					COMMA4=(Token)match(input,COMMA,FOLLOW_COMMA_in_hintList136);  
					stream_COMMA.add(COMMA4);

					pushFollow(FOLLOW_hintItem_in_hintList138);
					hintItem5=hintItem();
					state._fsp--;

					stream_hintItem.add(hintItem5.getTree());
					}
					break;

				default :
					break loop1;
				}
			}

			// AST REWRITE
			// elements: hintItem
			// token labels: 
			// rule labels: retval
			// token list labels: 
			// rule list labels: 
			// wildcard labels: 
			retval.tree = root_0;
			RewriteRuleSubtreeStream stream_retval=new RewriteRuleSubtreeStream(adaptor,"rule retval",retval!=null?retval.getTree():null);

			root_0 = (ASTNode)adaptor.nil();
			// 61:32: -> ( hintItem )+
			{
				if ( !(stream_hintItem.hasNext()) ) {
					throw new RewriteEarlyExitException();
				}
				while ( stream_hintItem.hasNext() ) {
					adaptor.addChild(root_0, stream_hintItem.nextTree());
				}
				stream_hintItem.reset();

			}


			retval.tree = root_0;

			}

			retval.stop = input.LT(-1);

			retval.tree = (ASTNode)adaptor.rulePostProcessing(root_0);
			adaptor.setTokenBoundaries(retval.tree, retval.start, retval.stop);

		}
		catch (RecognitionException re) {
			reportError(re);
			recover(input,re);
			retval.tree = (ASTNode)adaptor.errorNode(input, retval.start, input.LT(-1), re);
		}
		finally {
			// do for sure before leaving
		}
		return retval;
	}
	// $ANTLR end "hintList"


	public static class hintItem_return extends ParserRuleReturnScope {
		ASTNode tree;
		@Override
		public ASTNode getTree() { return tree; }
	};


	// $ANTLR start "hintItem"
	// org/apache/hadoop/hive/ql/parse/HintParser.g:64:1: hintItem : hintName ( LPAREN hintArgs RPAREN )? -> ^( TOK_HINT hintName ( hintArgs )? ) ;
	public final HintParser.hintItem_return hintItem() throws RecognitionException {
		HintParser.hintItem_return retval = new HintParser.hintItem_return();
		retval.start = input.LT(1);

		ASTNode root_0 = null;

		Token LPAREN7=null;
		Token RPAREN9=null;
		ParserRuleReturnScope hintName6 =null;
		ParserRuleReturnScope hintArgs8 =null;

		ASTNode LPAREN7_tree=null;
		ASTNode RPAREN9_tree=null;
		RewriteRuleTokenStream stream_LPAREN=new RewriteRuleTokenStream(adaptor,"token LPAREN");
		RewriteRuleTokenStream stream_RPAREN=new RewriteRuleTokenStream(adaptor,"token RPAREN");
		RewriteRuleSubtreeStream stream_hintName=new RewriteRuleSubtreeStream(adaptor,"rule hintName");
		RewriteRuleSubtreeStream stream_hintArgs=new RewriteRuleSubtreeStream(adaptor,"rule hintArgs");

		try {
			// org/apache/hadoop/hive/ql/parse/HintParser.g:65:5: ( hintName ( LPAREN hintArgs RPAREN )? -> ^( TOK_HINT hintName ( hintArgs )? ) )
			// org/apache/hadoop/hive/ql/parse/HintParser.g:66:5: hintName ( LPAREN hintArgs RPAREN )?
			{
			pushFollow(FOLLOW_hintName_in_hintItem166);
			hintName6=hintName();
			state._fsp--;

			stream_hintName.add(hintName6.getTree());
			// org/apache/hadoop/hive/ql/parse/HintParser.g:66:14: ( LPAREN hintArgs RPAREN )?
			int alt2=2;
			int LA2_0 = input.LA(1);
			if ( (LA2_0==LPAREN) ) {
				alt2=1;
			}
			switch (alt2) {
				case 1 :
					// org/apache/hadoop/hive/ql/parse/HintParser.g:66:15: LPAREN hintArgs RPAREN
					{
					LPAREN7=(Token)match(input,LPAREN,FOLLOW_LPAREN_in_hintItem169);  
					stream_LPAREN.add(LPAREN7);

					pushFollow(FOLLOW_hintArgs_in_hintItem171);
					hintArgs8=hintArgs();
					state._fsp--;

					stream_hintArgs.add(hintArgs8.getTree());
					RPAREN9=(Token)match(input,RPAREN,FOLLOW_RPAREN_in_hintItem173);  
					stream_RPAREN.add(RPAREN9);

					}
					break;

			}

			// AST REWRITE
			// elements: hintName, hintArgs
			// token labels: 
			// rule labels: retval
			// token list labels: 
			// rule list labels: 
			// wildcard labels: 
			retval.tree = root_0;
			RewriteRuleSubtreeStream stream_retval=new RewriteRuleSubtreeStream(adaptor,"rule retval",retval!=null?retval.getTree():null);

			root_0 = (ASTNode)adaptor.nil();
			// 66:40: -> ^( TOK_HINT hintName ( hintArgs )? )
			{
				// org/apache/hadoop/hive/ql/parse/HintParser.g:66:43: ^( TOK_HINT hintName ( hintArgs )? )
				{
				ASTNode root_1 = (ASTNode)adaptor.nil();
				root_1 = (ASTNode)adaptor.becomeRoot((ASTNode)adaptor.create(TOK_HINT, "TOK_HINT"), root_1);
				adaptor.addChild(root_1, stream_hintName.nextTree());
				// org/apache/hadoop/hive/ql/parse/HintParser.g:66:63: ( hintArgs )?
				if ( stream_hintArgs.hasNext() ) {
					adaptor.addChild(root_1, stream_hintArgs.nextTree());
				}
				stream_hintArgs.reset();

				adaptor.addChild(root_0, root_1);
				}

			}


			retval.tree = root_0;

			}

			retval.stop = input.LT(-1);

			retval.tree = (ASTNode)adaptor.rulePostProcessing(root_0);
			adaptor.setTokenBoundaries(retval.tree, retval.start, retval.stop);

		}
		catch (RecognitionException re) {
			reportError(re);
			recover(input,re);
			retval.tree = (ASTNode)adaptor.errorNode(input, retval.start, input.LT(-1), re);
		}
		finally {
			// do for sure before leaving
		}
		return retval;
	}
	// $ANTLR end "hintItem"


	public static class hintName_return extends ParserRuleReturnScope {
		ASTNode tree;
		@Override
		public ASTNode getTree() { return tree; }
	};


	// $ANTLR start "hintName"
	// org/apache/hadoop/hive/ql/parse/HintParser.g:69:1: hintName : ( KW_MAPJOIN -> TOK_MAPJOIN | KW_STREAMTABLE -> TOK_STREAMTABLE );
	public final HintParser.hintName_return hintName() throws RecognitionException {
		HintParser.hintName_return retval = new HintParser.hintName_return();
		retval.start = input.LT(1);

		ASTNode root_0 = null;

		Token KW_MAPJOIN10=null;
		Token KW_STREAMTABLE11=null;

		ASTNode KW_MAPJOIN10_tree=null;
		ASTNode KW_STREAMTABLE11_tree=null;
		RewriteRuleTokenStream stream_KW_MAPJOIN=new RewriteRuleTokenStream(adaptor,"token KW_MAPJOIN");
		RewriteRuleTokenStream stream_KW_STREAMTABLE=new RewriteRuleTokenStream(adaptor,"token KW_STREAMTABLE");

		try {
			// org/apache/hadoop/hive/ql/parse/HintParser.g:70:5: ( KW_MAPJOIN -> TOK_MAPJOIN | KW_STREAMTABLE -> TOK_STREAMTABLE )
			int alt3=2;
			int LA3_0 = input.LA(1);
			if ( (LA3_0==KW_MAPJOIN) ) {
				alt3=1;
			}
			else if ( (LA3_0==KW_STREAMTABLE) ) {
				alt3=2;
			}

			else {
				NoViableAltException nvae =
					new NoViableAltException("", 3, 0, input);
				throw nvae;
			}

			switch (alt3) {
				case 1 :
					// org/apache/hadoop/hive/ql/parse/HintParser.g:71:5: KW_MAPJOIN
					{
					KW_MAPJOIN10=(Token)match(input,KW_MAPJOIN,FOLLOW_KW_MAPJOIN_in_hintName207);  
					stream_KW_MAPJOIN.add(KW_MAPJOIN10);

					// AST REWRITE
					// elements: 
					// token labels: 
					// rule labels: retval
					// token list labels: 
					// rule list labels: 
					// wildcard labels: 
					retval.tree = root_0;
					RewriteRuleSubtreeStream stream_retval=new RewriteRuleSubtreeStream(adaptor,"rule retval",retval!=null?retval.getTree():null);

					root_0 = (ASTNode)adaptor.nil();
					// 71:16: -> TOK_MAPJOIN
					{
						adaptor.addChild(root_0, (ASTNode)adaptor.create(TOK_MAPJOIN, "TOK_MAPJOIN"));
					}


					retval.tree = root_0;

					}
					break;
				case 2 :
					// org/apache/hadoop/hive/ql/parse/HintParser.g:72:7: KW_STREAMTABLE
					{
					KW_STREAMTABLE11=(Token)match(input,KW_STREAMTABLE,FOLLOW_KW_STREAMTABLE_in_hintName219);  
					stream_KW_STREAMTABLE.add(KW_STREAMTABLE11);

					// AST REWRITE
					// elements: 
					// token labels: 
					// rule labels: retval
					// token list labels: 
					// rule list labels: 
					// wildcard labels: 
					retval.tree = root_0;
					RewriteRuleSubtreeStream stream_retval=new RewriteRuleSubtreeStream(adaptor,"rule retval",retval!=null?retval.getTree():null);

					root_0 = (ASTNode)adaptor.nil();
					// 72:22: -> TOK_STREAMTABLE
					{
						adaptor.addChild(root_0, (ASTNode)adaptor.create(TOK_STREAMTABLE, "TOK_STREAMTABLE"));
					}


					retval.tree = root_0;

					}
					break;

			}
			retval.stop = input.LT(-1);

			retval.tree = (ASTNode)adaptor.rulePostProcessing(root_0);
			adaptor.setTokenBoundaries(retval.tree, retval.start, retval.stop);

		}
		catch (RecognitionException re) {
			reportError(re);
			recover(input,re);
			retval.tree = (ASTNode)adaptor.errorNode(input, retval.start, input.LT(-1), re);
		}
		finally {
			// do for sure before leaving
		}
		return retval;
	}
	// $ANTLR end "hintName"


	public static class hintArgs_return extends ParserRuleReturnScope {
		ASTNode tree;
		@Override
		public ASTNode getTree() { return tree; }
	};


	// $ANTLR start "hintArgs"
	// org/apache/hadoop/hive/ql/parse/HintParser.g:75:1: hintArgs : hintArgName ( COMMA hintArgName )* -> ^( TOK_HINTARGLIST ( hintArgName )+ ) ;
	public final HintParser.hintArgs_return hintArgs() throws RecognitionException {
		HintParser.hintArgs_return retval = new HintParser.hintArgs_return();
		retval.start = input.LT(1);

		ASTNode root_0 = null;

		Token COMMA13=null;
		ParserRuleReturnScope hintArgName12 =null;
		ParserRuleReturnScope hintArgName14 =null;

		ASTNode COMMA13_tree=null;
		RewriteRuleTokenStream stream_COMMA=new RewriteRuleTokenStream(adaptor,"token COMMA");
		RewriteRuleSubtreeStream stream_hintArgName=new RewriteRuleSubtreeStream(adaptor,"rule hintArgName");

		try {
			// org/apache/hadoop/hive/ql/parse/HintParser.g:76:5: ( hintArgName ( COMMA hintArgName )* -> ^( TOK_HINTARGLIST ( hintArgName )+ ) )
			// org/apache/hadoop/hive/ql/parse/HintParser.g:77:5: hintArgName ( COMMA hintArgName )*
			{
			pushFollow(FOLLOW_hintArgName_in_hintArgs244);
			hintArgName12=hintArgName();
			state._fsp--;

			stream_hintArgName.add(hintArgName12.getTree());
			// org/apache/hadoop/hive/ql/parse/HintParser.g:77:17: ( COMMA hintArgName )*
			loop4:
			while (true) {
				int alt4=2;
				int LA4_0 = input.LA(1);
				if ( (LA4_0==COMMA) ) {
					alt4=1;
				}

				switch (alt4) {
				case 1 :
					// org/apache/hadoop/hive/ql/parse/HintParser.g:77:18: COMMA hintArgName
					{
					COMMA13=(Token)match(input,COMMA,FOLLOW_COMMA_in_hintArgs247);  
					stream_COMMA.add(COMMA13);

					pushFollow(FOLLOW_hintArgName_in_hintArgs249);
					hintArgName14=hintArgName();
					state._fsp--;

					stream_hintArgName.add(hintArgName14.getTree());
					}
					break;

				default :
					break loop4;
				}
			}

			// AST REWRITE
			// elements: hintArgName
			// token labels: 
			// rule labels: retval
			// token list labels: 
			// rule list labels: 
			// wildcard labels: 
			retval.tree = root_0;
			RewriteRuleSubtreeStream stream_retval=new RewriteRuleSubtreeStream(adaptor,"rule retval",retval!=null?retval.getTree():null);

			root_0 = (ASTNode)adaptor.nil();
			// 77:38: -> ^( TOK_HINTARGLIST ( hintArgName )+ )
			{
				// org/apache/hadoop/hive/ql/parse/HintParser.g:77:41: ^( TOK_HINTARGLIST ( hintArgName )+ )
				{
				ASTNode root_1 = (ASTNode)adaptor.nil();
				root_1 = (ASTNode)adaptor.becomeRoot((ASTNode)adaptor.create(TOK_HINTARGLIST, "TOK_HINTARGLIST"), root_1);
				if ( !(stream_hintArgName.hasNext()) ) {
					throw new RewriteEarlyExitException();
				}
				while ( stream_hintArgName.hasNext() ) {
					adaptor.addChild(root_1, stream_hintArgName.nextTree());
				}
				stream_hintArgName.reset();

				adaptor.addChild(root_0, root_1);
				}

			}


			retval.tree = root_0;

			}

			retval.stop = input.LT(-1);

			retval.tree = (ASTNode)adaptor.rulePostProcessing(root_0);
			adaptor.setTokenBoundaries(retval.tree, retval.start, retval.stop);

		}
		catch (RecognitionException re) {
			reportError(re);
			recover(input,re);
			retval.tree = (ASTNode)adaptor.errorNode(input, retval.start, input.LT(-1), re);
		}
		finally {
			// do for sure before leaving
		}
		return retval;
	}
	// $ANTLR end "hintArgs"


	public static class hintArgName_return extends ParserRuleReturnScope {
		ASTNode tree;
		@Override
		public ASTNode getTree() { return tree; }
	};


	// $ANTLR start "hintArgName"
	// org/apache/hadoop/hive/ql/parse/HintParser.g:80:1: hintArgName : Identifier ;
	public final HintParser.hintArgName_return hintArgName() throws RecognitionException {
		HintParser.hintArgName_return retval = new HintParser.hintArgName_return();
		retval.start = input.LT(1);

		ASTNode root_0 = null;

		Token Identifier15=null;

		ASTNode Identifier15_tree=null;

		try {
			// org/apache/hadoop/hive/ql/parse/HintParser.g:81:5: ( Identifier )
			// org/apache/hadoop/hive/ql/parse/HintParser.g:82:5: Identifier
			{
			root_0 = (ASTNode)adaptor.nil();


			Identifier15=(Token)match(input,Identifier,FOLLOW_Identifier_in_hintArgName281); 
			Identifier15_tree = (ASTNode)adaptor.create(Identifier15);
			adaptor.addChild(root_0, Identifier15_tree);

			}

			retval.stop = input.LT(-1);

			retval.tree = (ASTNode)adaptor.rulePostProcessing(root_0);
			adaptor.setTokenBoundaries(retval.tree, retval.start, retval.stop);

		}
		catch (RecognitionException re) {
			reportError(re);
			recover(input,re);
			retval.tree = (ASTNode)adaptor.errorNode(input, retval.start, input.LT(-1), re);
		}
		finally {
			// do for sure before leaving
		}
		return retval;
	}
	// $ANTLR end "hintArgName"

	// Delegated rules



	public static final BitSet FOLLOW_hintList_in_hint102 = new BitSet(new long[]{0x0000000000000000L});
	public static final BitSet FOLLOW_EOF_in_hint104 = new BitSet(new long[]{0x0000000000000002L});
	public static final BitSet FOLLOW_hintItem_in_hintList133 = new BitSet(new long[]{0x0000000000000202L});
	public static final BitSet FOLLOW_COMMA_in_hintList136 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000000L,0x0008000000000000L,0x0000000000000000L,0x0000000000100000L});
	public static final BitSet FOLLOW_hintItem_in_hintList138 = new BitSet(new long[]{0x0000000000000202L});
	public static final BitSet FOLLOW_hintName_in_hintItem166 = new BitSet(new long[]{0x0000000000000002L,0x0000000000000000L,0x0000000000000000L,0x0000000000000000L,0x0000000000000000L,0x0000000000004000L});
	public static final BitSet FOLLOW_LPAREN_in_hintItem169 = new BitSet(new long[]{0x0000000001000000L});
	public static final BitSet FOLLOW_hintArgs_in_hintItem171 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000000L,0x0000000000000000L,0x0000000000000000L,0x0000000000000000L,0x0000000008000000L});
	public static final BitSet FOLLOW_RPAREN_in_hintItem173 = new BitSet(new long[]{0x0000000000000002L});
	public static final BitSet FOLLOW_KW_MAPJOIN_in_hintName207 = new BitSet(new long[]{0x0000000000000002L});
	public static final BitSet FOLLOW_KW_STREAMTABLE_in_hintName219 = new BitSet(new long[]{0x0000000000000002L});
	public static final BitSet FOLLOW_hintArgName_in_hintArgs244 = new BitSet(new long[]{0x0000000000000202L});
	public static final BitSet FOLLOW_COMMA_in_hintArgs247 = new BitSet(new long[]{0x0000000001000000L});
	public static final BitSet FOLLOW_hintArgName_in_hintArgs249 = new BitSet(new long[]{0x0000000000000202L});
	public static final BitSet FOLLOW_Identifier_in_hintArgName281 = new BitSet(new long[]{0x0000000000000002L});
}
