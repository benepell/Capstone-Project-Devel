/*
 *
 * ______                _____ _
 * | ___ \              /  ___| |
 * | |_/ /___ __ _ _ __ \ `--.| |_ ___  _ __   ___
 * |    // __/ _` | '_ \ `--. \ __/ _ \| '_ \ / _ \
 * | |\ \ (_| (_| | |_) /\__/ / || (_) | | | |  __/
 * \_| \_\___\__,_| .__/\____/ \__\___/|_| |_|\___|
 *                | |
 *                |_|
 *
 * Copyright (C) 2018 Benedetto Pellerito
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package info.pelleritoudacity.android.rcapstone.data.db;

import android.content.ContentResolver;
import android.net.Uri;
import android.provider.BaseColumns;

@SuppressWarnings("WeakerAccess")
public class Contract {
    public static final String CONTENT_AUTHORITY = "info.pelleritoudacity.android.rcapstone";

    @SuppressWarnings("WeakerAccess")
    public static final Uri BASE_CONTENT_URI = Uri.parse("content://" + CONTENT_AUTHORITY);

    public static final String PATH_REDDITS = "_reddits";
    public static final String PATH_DATAS = "_datas";
    public static final String PATH_T5DATAS = "_t5datas";
    public static final String PATH_T3DATAS = "_t3datas";
    public static final String PATH_T1DATAS = "_t1datas";
    public static final String PATH_T1MORESDATAS = "_t1moresdatas";
    public static final String PATH_PREFSUBREDDIT = "_prefsubreddit";


    private Contract() {
    }

    public static class RedditEntry implements BaseColumns {

        public static final Uri CONTENT_URI =
                BASE_CONTENT_URI.buildUpon().appendPath(PATH_REDDITS).build();

        public static final String CONTENT_TYPE =
                ContentResolver.CURSOR_DIR_BASE_TYPE + "/" + CONTENT_AUTHORITY + "/" + PATH_REDDITS;

        public static final String CONTENT_ITEM_TYPE =
                ContentResolver.CURSOR_ITEM_BASE_TYPE + "/" + CONTENT_AUTHORITY + "/" + PATH_REDDITS;

        public static final String TABLE_NAME = "_reddits";
        public static final String COLUMN_NAME_KIND = "_kind";
        public static final String COLUMN_NAME_DATA = "_data";

    }

    public static class PrefSubRedditEntry implements BaseColumns {

        public static final Uri CONTENT_URI =
                BASE_CONTENT_URI.buildUpon().appendPath(PATH_PREFSUBREDDIT).build();

        public static final String CONTENT_TYPE =
                ContentResolver.CURSOR_DIR_BASE_TYPE + "/" + CONTENT_AUTHORITY + "/" + PATH_PREFSUBREDDIT;

        public static final String CONTENT_ITEM_TYPE =
                ContentResolver.CURSOR_ITEM_BASE_TYPE + "/" + CONTENT_AUTHORITY + "/" + PATH_PREFSUBREDDIT;

        public static final String TABLE_NAME = "_prefsubreddit";
        public static final String COLUMN_NAME_NAME = "_name";
        public static final String COLUMN_NAME_IMAGE = "_image";
        public static final String COLUMN_NAME_POSITION = "_position";
        public static final String COLUMN_NAME_BACKUP_POSITION = "_backup_position";
        public static final String COLUMN_NAME_VISIBLE = "_visible";
        public static final String COLUMN_NAME_REMOVED = "_removed";
        public static final String COLUMN_NAME_TIME_LAST_MODIFIED = "_time_last_modified";

    }

    public static class DataEntry implements BaseColumns {

        public static final Uri CONTENT_URI =
                BASE_CONTENT_URI.buildUpon().appendPath(PATH_DATAS).build();

        public static final String CONTENT_TYPE =
                ContentResolver.CURSOR_DIR_BASE_TYPE + "/" + CONTENT_AUTHORITY + "/" + PATH_DATAS;

        public static final String CONTENT_ITEM_TYPE =
                ContentResolver.CURSOR_ITEM_BASE_TYPE + "/" + CONTENT_AUTHORITY + "/" + PATH_DATAS;

        public static final String TABLE_NAME = "_datas";
        public static final String COLUMN_NAME_AFTER = "_after";
        public static final String COLUMN_NAME_DIST = "_dist";
        public static final String COLUMN_NAME_MODHASH = "_modhash";
        public static final String COLUMN_NAME_WHITELIST_STATUS = "_whitelist_status";
        public static final String COLUMN_NAME_CHILDRENS = "_childrens";
        public static final String COLUMN_NAME_BEFORE = "_before";

    }

    public static class T5dataEntry implements BaseColumns {

        public static final Uri CONTENT_URI =
                BASE_CONTENT_URI.buildUpon().appendPath(PATH_T5DATAS).build();

        public static final String CONTENT_TYPE =
                ContentResolver.CURSOR_DIR_BASE_TYPE + "/" + CONTENT_AUTHORITY + "/" + PATH_T5DATAS;

        public static final String CONTENT_ITEM_TYPE =
                ContentResolver.CURSOR_ITEM_BASE_TYPE + "/" + CONTENT_AUTHORITY + "/" + PATH_T5DATAS;

        public static final String TABLE_NAME = "_t5datas";
        public static final String COLUMN_NAME_SUGGESTED_COMMENT_SORT = "_suggested_comment_sort";
        public static final String COLUMN_NAME_HIDE_ADS = "_hide_ads";
        public static final String COLUMN_NAME_BANNER_IMG = "_banner_img";
        public static final String COLUMN_NAME_USER_SR_THEME_ENABLED = "_user_sr_theme_enabled";
        public static final String COLUMN_NAME_USER_FLAIR_TEXT = "_user_flair_text";
        public static final String COLUMN_NAME_SUBMIT_TEXT_HTML = "_submit_text_html";
        public static final String COLUMN_NAME_USER_IS_BANNED = "_user_is_banned";
        public static final String COLUMN_NAME_WIKI_ENABLED = "_wiki_enabled";
        public static final String COLUMN_NAME_SHOW_MEDIA = "_show_media";
        public static final String COLUMN_NAME_ID = "_name_id";
        public static final String COLUMN_NAME_CHILDREN_ID = "_name_children_id";
        public static final String COLUMN_NAME_DISPLAY_NAME_PREFIXED = "_display_name_prefixed";
        public static final String COLUMN_NAME_SUBMIT_TEXT = "_submit_text";
        public static final String COLUMN_NAME_USER_CAN_FLAIR_IN_SR = "_user_can_flair_in_sr";
        public static final String COLUMN_NAME_DISPLAY_NAME = "_display_name";
        public static final String COLUMN_NAME_HEADER_IMG = "_header_img";
        public static final String COLUMN_NAME_DESCRIPTION_HTML = "_description_html";
        public static final String COLUMN_NAME_TITLE = "_title";
        public static final String COLUMN_NAME_COLLAPSE_DELETED_COMMENTS = "_collapse_deleted_comments";
        public static final String COLUMN_NAME_USER_HAS_FAVORITED = "_user_has_favorited";
        public static final String COLUMN_NAME_OVER18 = "_over18";
        public static final String COLUMN_NAME_PUBLIC_DESCRIPTION_HTML = "_public_description_html";
        public static final String COLUMN_NAME_ALLOW_VIDEOS = "_allow_videos";
        public static final String COLUMN_NAME_SPOILERS_ENABLED = "_spoilers_enabled";
        public static final String COLUMN_NAME_ICON_SIZE = "_icon_size";
        public static final String COLUMN_NAME_AUDIENCE_TARGET = "_audience_target";
        public static final String COLUMN_NAME_NOTIFICATION_LEVEL = "_notification_level";
        public static final String COLUMN_NAME_ACTIVE_USER_COUNT = "_active_user_count";
        public static final String COLUMN_NAME_ICON_IMG = "_icon_img";
        public static final String COLUMN_NAME_HEADER_TITLE = "_header_title";
        public static final String COLUMN_NAME_DESCRIPTION = "_description";
        public static final String COLUMN_NAME_USER_IS_MUTED = "_user_is_muted";
        public static final String COLUMN_NAME_SUBMIT_LINK_LABEL = "_submit_link_label";
        public static final String COLUMN_NAME_ACCOUNTS_ACTIVE = "_accounts_active";
        public static final String COLUMN_NAME_PUBLIC_TRAFFIC = "_public_traffic";
        public static final String COLUMN_NAME_HEADER_SIZE = "_header_size";
        public static final String COLUMN_NAME_SUBSCRIBERS = "_subscribers";
        public static final String COLUMN_NAME_USER_FLAIR_CSS_CLASS = "_user_flair_css_class";
        public static final String COLUMN_NAME_SUBMIT_TEXT_LABEL = "_submit_text_label";
        public static final String COLUMN_NAME_WHITELIST_STATUS = "_whitelist_status";
        public static final String COLUMN_NAME_USER_SR_FLAIR_ENABLED = "_user_sr_flair_enabled";
        public static final String COLUMN_NAME_LANG = "_lang";
        public static final String COLUMN_NAME_USER_IS_MODERATOR = "_user_is_moderator";
        public static final String COLUMN_NAME_IS_ENROLLED_IN_NEW_MODMAIL = "_is_enrolled_in_new_modmail";
        public static final String COLUMN_NAME_KEY_COLOR = "_key_color";
        public static final String COLUMN_NAME_NAME = "_name";
        public static final String COLUMN_NAME_USER_FLAIR_ENABLED_IN_SR = "_user_flair_enabled_in_sr";
        public static final String COLUMN_NAME_ALLOW_VIDEOGIFS = "_allow_videogifs";
        public static final String COLUMN_NAME_URL = "_url";
        public static final String COLUMN_NAME_QUARANTINE = "_quarantine";
        public static final String COLUMN_NAME_CREATED = "_created";
        public static final String COLUMN_NAME_CREATED_UTC = "_created_utc";
        public static final String COLUMN_NAME_BANNER_SIZE = "_banner_size";
        public static final String COLUMN_NAME_USER_IS_CONTRIBUTOR = "_user_is_contributor";
        public static final String COLUMN_NAME_ALLOW_DISCOVERY = "_allow_discovery";
        public static final String COLUMN_NAME_ACCOUNTS_ACTIVE_IS_FUZZED = "_accounts_active_is_fuzzed";
        public static final String COLUMN_NAME_ADVERTISER_CATEGORY = "_advertiser_category";
        public static final String COLUMN_NAME_PUBLIC_DESCRIPTION = "_public_description";
        public static final String COLUMN_NAME_LINK_FLAIR_ENABLED = "_link_flair_enabled";
        public static final String COLUMN_NAME_ALLOW_IMAGES = "_allow_images";
        public static final String COLUMN_NAME_SHOW_MEDIA_PREVIEW = "_show_media_preview";
        public static final String COLUMN_NAME_COMMENT_SCORE_HIDE_MINS = "_comment_score_hide_mins";
        public static final String COLUMN_NAME_SUBREDDIT_TYPE = "_subreddit_type";
        public static final String COLUMN_NAME_SUBMISSION_TYPE = "_submission_type";
        public static final String COLUMN_NAME_USER_IS_SUBSCRIBER = "_user_is_subscriber";
        public static final String COLUMN_NAME_TIME_LAST_MODIFIED = "_time_last_modified";
        public static final String COLUMN_NAME_SORT_BY = "_sort_by";


    }

    public static class T3dataEntry implements BaseColumns {

        public static final Uri CONTENT_URI =
                BASE_CONTENT_URI.buildUpon().appendPath(PATH_T3DATAS).build();

        public static final String CONTENT_TYPE =
                ContentResolver.CURSOR_DIR_BASE_TYPE + "/" + CONTENT_AUTHORITY + "/" + PATH_T3DATAS;

        public static final String CONTENT_ITEM_TYPE =
                ContentResolver.CURSOR_ITEM_BASE_TYPE + "/" + CONTENT_AUTHORITY + "/" + PATH_T3DATAS;

        public static final String TABLE_NAME = "_t3datas";
        public static final String COLUMN_NAME_IS_CROSSPOSTABLE = "_is_crosspostable";
        public static final String COLUMN_NAME_SUBREDDIT_ID = "_subreddit_id";
        public static final String COLUMN_NAME_APPROVED_AT_UTC = "_approved_at_utc";
        public static final String COLUMN_NAME_WLS = "_wls";
        public static final String COLUMN_NAME_MOD_REASON_BY = "_mod_reason_by";
        public static final String COLUMN_NAME_BANNED_BY = "_banned_by";
        public static final String COLUMN_NAME_NUM_REPORTS = "_num_reports";
        public static final String COLUMN_NAME_REMOVAL_REASON = "_removal_reason";
        public static final String COLUMN_NAME_THUMBNAIL_WIDTH = "_thumbnail_width";
        public static final String COLUMN_NAME_SUBREDDIT = "_subreddit";
        public static final String COLUMN_NAME_SELFTEXT_HTML = "_selftext_html";
        public static final String COLUMN_NAME_SELFTEXT = "_selftext";
        public static final String COLUMN_NAME_LIKES = "_likes";
        public static final String COLUMN_NAME_SUGGESTED_SORT = "_suggested_sort";
        public static final String COLUMN_NAME_USER_REPORTS = "_user_reports";
        public static final String COLUMN_NAME_SECURE_MEDIA = "_secure_media";
        public static final String COLUMN_NAME_IS_REDDIT_MEDIA_DOMAIN = "_is_reddit_media_domain";
        public static final String COLUMN_NAME_SAVED = "_saved";
        public static final String COLUMN_NAME_ID = "_name_id";
        public static final String COLUMN_NAME_BANNED_AT_UTC = "_banned_at_utc";
        public static final String COLUMN_NAME_MOD_REASON_TITLE = "_mod_reason_title";
        public static final String COLUMN_NAME_VIEW_COUNT = "_view_count";
        public static final String COLUMN_NAME_ARCHIVED = "_archived";
        public static final String COLUMN_NAME_CLICKED = "_clicked";
        public static final String COLUMN_NAME_NO_FOLLOW = "_no_follow";
        public static final String COLUMN_NAME_AUTHOR = "_author";
        public static final String COLUMN_NAME_NUM_CROSSPOSTS = "_num_crossposts";
        public static final String COLUMN_NAME_LINK_FLAIR_TEXT = "_link_flair_text";
        public static final String COLUMN_NAME_CAN_MOD_POST = "_can_mod_post";
        public static final String COLUMN_NAME_SEND_REPLIES = "_send_replies";
        public static final String COLUMN_NAME_PINNED = "_pinned";
        public static final String COLUMN_NAME_SCORE = "_score";
        public static final String COLUMN_NAME_APPROVED_BY = "_approved_by";
        public static final String COLUMN_NAME_OVER_18 = "_over_18";
        public static final String COLUMN_NAME_REPORT_REASONS = "_report_reasons";
        public static final String COLUMN_NAME_DOMAIN = "_domain";
        public static final String COLUMN_NAME_HIDDEN = "_hidden";
        public static final String COLUMN_NAME_PWLS = "_pwls";
        public static final String COLUMN_NAME_THUMBNAIL = "_thumbnail";
        public static final String COLUMN_NAME_EDITED = "_edited";
        public static final String COLUMN_NAME_LINK_FLAIR_CSS_CLASS = "_link_flair_css_class";
        public static final String COLUMN_NAME_AUTHOR_FLAIR_CSS_CLASS = "_author_flair_css_class";
        public static final String COLUMN_NAME_CONTEST_MODE = "_contest_mode";
        public static final String COLUMN_NAME_GILDED = "_gilded";
        public static final String COLUMN_NAME_LOCKED = "_locked";
        public static final String COLUMN_NAME_DOWNS = "_downs";
        public static final String COLUMN_NAME_MOD_REPORTS = "_mod_reports";
        public static final String COLUMN_NAME_SUBREDDIT_SUBSCRIBERS = "_subreddit_subscribers";
        public static final String COLUMN_NAME_SECURE_MEDIA_EMBED = "_secure_media_embed";
        public static final String COLUMN_NAME_MEDIA_EMBED = "_media_embed";
        public static final String COLUMN_NAME_STICKIED = "_stickied";
        public static final String COLUMN_NAME_VISITED = "_visited";
        public static final String COLUMN_NAME_CAN_GILD = "_can_gild";
        public static final String COLUMN_NAME_THUMBNAIL_HEIGHT = "_thumbnail_height";
        public static final String COLUMN_NAME_NAME = "_name";
        public static final String COLUMN_NAME_SPOILER = "_spoiler";
        public static final String COLUMN_NAME_PERMALINK = "_permalink";
        public static final String COLUMN_NAME_SUBREDDIT_TYPE = "_subreddit_type";
        public static final String COLUMN_NAME_PARENT_WHITELIST_STATUS = "_parent_whitelist_status";
        public static final String COLUMN_NAME_HIDE_SCORE = "_hide_score";
        public static final String COLUMN_NAME_CREATED = "_created";
        public static final String COLUMN_NAME_URL = "_url";
        public static final String COLUMN_NAME_AUTHOR_FLAIR_TEXT = "_author_flair_text";
        public static final String COLUMN_NAME_QUARANTINE = "_quarantine";
        public static final String COLUMN_NAME_TITLE = "_title";
        public static final String COLUMN_NAME_CREATED_UTC = "_created_utc";
        public static final String COLUMN_NAME_SUBREDDIT_NAME_PREFIXE = "_subreddit_name_prefixed";
        public static final String COLUMN_NAME_UPS = "_ups";
        public static final String COLUMN_NAME_NUM_COMMENTS = "_num_comments";
        public static final String COLUMN_NAME_MEDIA = "_media";
        public static final String COLUMN_NAME_IS_SELF = "_is_self";
        public static final String COLUMN_NAME_WHITELIST_STATUS = "_whitelist_status";
        public static final String COLUMN_NAME_MOD_NOTE = "_mod_note";
        public static final String COLUMN_NAME_IS_VIDEO = "_is_video";
        public static final String COLUMN_NAME_DISTINGUISHED = "_distinguished";
        public static final String COLUMN_NAME_CHILDREN_ID = "_children_id";

        public static final String COLUMN_NAME_TARGET = "_target";
        public static final String COLUMN_NAME_SORT_BY = "_sort_by";


        public static final String COLUMN_NAME_PREVIEW_IMAGE_SOURCE_URL = "_preview_image_source_url";
        public static final String COLUMN_NAME_PREVIEW_IMAGE_SOURCE_WIDTH = "_preview_image_source_width";
        public static final String COLUMN_NAME_PREVIEW_IMAGE_SOURCE_HEIGHT = "_preview_image_source_height";

        public static final String COLUMN_NAME_PREVIEW_VIDEO_HLS_URL = "_preview_video_hls_url";
        public static final String COLUMN_NAME_PREVIEW_VIDEO_DASH_URL = "_preview_video_dash_url";
        public static final String COLUMN_NAME_PREVIEW_VIDEO_SCRUBBER_MEDIA_URL = "_preview_video_scrubber_media_url";
        public static final String COLUMN_NAME_PREVIEW_VIDEO_FALLBACK_URL = "_preview_video_fallback_url";
        public static final String COLUMN_NAME_PREVIEW_VIDEO_TRANSCODING_STATUS = "_preview_video_transcoding_status";
        public static final String COLUMN_NAME_PREVIEW_VIDEO_DURATION = "_preview_video_duration";
        public static final String COLUMN_NAME_PREVIEW_VIDEO_WIDTH = "_preview_video_width";
        public static final String COLUMN_NAME_PREVIEW_VIDEO_HEIGHT = "_preview_video_height";
        public static final String COLUMN_NAME_PREVIEW_IS_VIDEO_GIF = "_is_video_gif";

        public static final String COLUMN_NAME_VARIANT_VIDEO_MP4_URL = "_variant_is_video_mp4_gif";
        public static final String COLUMN_NAME_VARIANT_VIDEO_MP4_WIDTH = "_variant_video_mp4_width";
        public static final String COLUMN_NAME_VARIANT_VIDEO_MP4_HEIGHT = "_variant_video_mp4_height";

        public static final String COLUMN_NAME_TIME_LAST_MODIFIED = "_time_last_modified";

        public static final String COLUMN_NAME_MEDIA_TYPE = "_media_type";
        public static final String COLUMN_NAME_MEDIA_IS_VIDEO = "_media_is_video";
        public static final String COLUMN_NAME_MEDIA_OEMBED_PROVIDER_URL = "_media_oembed_provider_url";
        public static final String COLUMN_NAME_MEDIA_OEMBED_TITLE = "_media_oembed_title";
        public static final String COLUMN_NAME_MEDIA_OEMBED_TYPE = "_media_oembed_type";
        public static final String COLUMN_NAME_MEDIA_OEMBED_HTML = "_media_oembed_html";
        public static final String COLUMN_NAME_MEDIA_OEMBED_AUTHOR_NAME = "_media_oembed_author_name";
        public static final String COLUMN_NAME_MEDIA_OEMBED_HEIGHT = "_media_oembed_height";
        public static final String COLUMN_NAME_MEDIA_OEMBED_WIDTH = "_media_oembed_width";
        public static final String COLUMN_NAME_MEDIA_OEMBED_VERSION = "_media_oembed_version";
        public static final String COLUMN_NAME_MEDIA_OEMBED_THUMBNAIL_WIDTH = "_media_oembed_thumbnail_width";
        public static final String COLUMN_NAME_MEDIA_OEMBED_PROVIDER_NAME = "_media_oembed_provider_name";
        @SuppressWarnings("SpellCheckingInspection")
        public static final String COLUMN_NAME_MEDIA_OEMBED_THUMBNAIL_URL = "_media_oembed_thumbnail_url";
        public static final String COLUMN_NAME_MEDIA_OEMBED_THUMBNAIL_HEIGHT = "_media_oembed_thumbnail_height";
        public static final String COLUMN_NAME_MEDIA_OEMBED_AUTHOR_URL = "_media_oembed_author_url";


    }

    public static class T1dataEntry implements BaseColumns {

        public static final Uri CONTENT_URI =
                BASE_CONTENT_URI.buildUpon().appendPath(PATH_T1DATAS).build();

        public static final String CONTENT_TYPE =
                ContentResolver.CURSOR_DIR_BASE_TYPE + "/" + CONTENT_AUTHORITY + "/" + PATH_T1DATAS;

        public static final String CONTENT_ITEM_TYPE =
                ContentResolver.CURSOR_ITEM_BASE_TYPE + "/" + CONTENT_AUTHORITY + "/" + PATH_T1DATAS;

        public static final String TABLE_NAME = "_t1datas";
        public static final String COLUMN_NAME_MORE_REPLIES = "_more_replies";
        public static final String COLUMN_NAME_MORE_LEVEL_REPLIES = "_more_level_replies";
        public static final String COLUMN_NAME_ISCROSSPOSTABLE = "_isCrosspostable";
        public static final String COLUMN_NAME_SUBREDDITID = "_subredditId";
        public static final String COLUMN_NAME_APPROVEDATUTC = "_approvedAtUtc";
        public static final String COLUMN_NAME_WLS = "_wls";
        public static final String COLUMN_NAME_MODREASONBY = "_modReasonBy";
        public static final String COLUMN_NAME_BANNEDBY = "_bannedBy";
        public static final String COLUMN_NAME_NUMREPORTS = "_numReports";
        public static final String COLUMN_NAME_LINK_ID = "_link_id";
        public static final String COLUMN_NAME_REMOVALREASON = "_removalReason";
        public static final String COLUMN_NAME_THUMBNAILWIDTH = "_thumbnailWidth";
        public static final String COLUMN_NAME_SUBREDDIT = "_subreddit";
        public static final String COLUMN_NAME_SELFTEXTHTML = "_selftextHtml";
        public static final String COLUMN_NAME_SELFTEXT = "_selftext";
        public static final String COLUMN_NAME_LIKES = "_likes";
        public static final String COLUMN_NAME_SUGGESTEDSORT = "_suggestedSort";
        public static final String COLUMN_NAME_USERREPORTS = "_userReports";
        public static final String COLUMN_NAME_SECUREMEDIA = "_secureMedia";
        public static final String COLUMN_NAME_ISREDDITMEDIADOMAIN = "_isRedditMediaDomain";
        public static final String COLUMN_NAME_SAVED = "_saved";
        public static final String COLUMN_NAME_ID = "_name_id";
        public static final String COLUMN_NAME_CHILDREN_ID = "_name_children_id";
        public static final String COLUMN_NAME_BANNEDATUTC = "_bannedAtUtc";
        public static final String COLUMN_NAME_MODREASONTITLE = "_modReasonTitle";
        public static final String COLUMN_NAME_VIEWCOUNT = "_viewCount";
        public static final String COLUMN_NAME_ARCHIVED = "_archived";
        public static final String COLUMN_NAME_CLICKED = "_clicked";
        public static final String COLUMN_NAME_NOFOLLOW = "_noFollow";
        public static final String COLUMN_NAME_AUTHOR = "_author";
        public static final String COLUMN_NAME_NUMCROSSPOSTS = "_numCrossposts";
        public static final String COLUMN_NAME_LINKFLAIRTEXT = "_linkFlairText";
        public static final String COLUMN_NAME_CANMODPOST = "_canModPost";
        public static final String COLUMN_NAME_SENDREPLIES = "_sendReplies";
        public static final String COLUMN_NAME_PINNED = "_pinned";
        public static final String COLUMN_NAME_SCORE = "_score";
        public static final String COLUMN_NAME_APPROVEDBY = "_approvedBy";
        public static final String COLUMN_NAME_OVER18 = "_over18";
        public static final String COLUMN_NAME_REPORTREASONS = "_reportReasons";
        public static final String COLUMN_NAME_DOMAIN = "_domain";
        public static final String COLUMN_NAME_HIDDEN = "_hidden";
        public static final String COLUMN_NAME_DEPTH = "_depth";
        public static final String COLUMN_NAME_PREVIEW = "_preview";
        public static final String COLUMN_NAME_PWLS = "_pwls";
        public static final String COLUMN_NAME_THUMBNAIL = "_thumbnail";
        public static final String COLUMN_NAME_EDITED = "_edited";
        public static final String COLUMN_NAME_LINKFLAIRCSSCLASS = "_linkFlairCssClass";
        public static final String COLUMN_NAME_AUTHORFLAIRCSSCLASS = "_authorFlairCssClass";
        public static final String COLUMN_NAME_CONTESTMODE = "_contestMode";
        public static final String COLUMN_NAME_GILDED = "_gilded";
        public static final String COLUMN_NAME_LOCKED = "_locked";
        public static final String COLUMN_NAME_DOWNS = "_downs";
        public static final String COLUMN_NAME_MODREPORTS = "_modReports";
        public static final String COLUMN_NAME_BODY = "_body";
        public static final String COLUMN_NAME_SUBREDDITSUBSCRIBERS = "_subredditSubscribers";
        public static final String COLUMN_NAME_SECUREMEDIAEMBED = "_secureMediaEmbed";
        public static final String COLUMN_NAME_MEDIAEMBED = "_mediaEmbed";
        public static final String COLUMN_NAME_STICKIED = "_stickied";
        public static final String COLUMN_NAME_VISITED = "_visited";
        public static final String COLUMN_NAME_CANGILD = "_canGild";
        public static final String COLUMN_NAME_THUMBNAILHEIGHT = "_thumbnailHeight";
        public static final String COLUMN_NAME_NAME = "_name";
        public static final String COLUMN_NAME_SPOILER = "_spoiler";
        public static final String COLUMN_NAME_PERMALINK = "_permalink";
        public static final String COLUMN_NAME_SUBREDDITTYPE = "_subredditType";
        public static final String COLUMN_NAME_PARENT_ID = "_parent_id";
        public static final String COLUMN_NAME_PARENTWHITELISTSTATUS = "_parentWhitelistStatus";
        public static final String COLUMN_NAME_HIDESCORE = "_hideScore";
        public static final String COLUMN_NAME_CREATED = "_created";
        public static final String COLUMN_NAME_URL = "_url";
        public static final String COLUMN_NAME_AUTHORFLAIRTEXT = "_authorFlairText";
        public static final String COLUMN_NAME_QUARANTINE = "_quarantine";
        public static final String COLUMN_NAME_TITLE = "_title";
        public static final String COLUMN_NAME_CREATEDUTC = "_createdUtc";
        public static final String COLUMN_NAME_SUBREDDITNAMEPREFIXED = "_subredditNamePrefixed";
        public static final String COLUMN_NAME_UPS = "_ups";
        public static final String COLUMN_NAME_NUMCOMMENTS = "_name_num_comments";
        public static final String COLUMN_NAME_MORECOMMENTS = "_name_more_comments";
        public static final String COLUMN_NAME_ISSELF = "_isSelf";
        public static final String COLUMN_NAME_WHITELISTSTATUS = "_whitelistStatus";
        public static final String COLUMN_NAME_BODY_HTML = "_body_html";
        public static final String COLUMN_NAME_MODNOTE = "_modNote";
        public static final String COLUMN_NAME_MEDIA = "_media";
        public static final String COLUMN_NAME_ISVIDEO = "_isVideo";
        public static final String COLUMN_NAME_DISTINGUISHED = "_distinguished";
        public static final String COLUMN_NAME_TIME_LAST_MODIFIED = "_time_last_modified";
        public static final String COLUMN_NAME_SORT_BY = "_sort_by";

    }


    public static class T1MoresDataEntry implements BaseColumns {

        public static final Uri CONTENT_URI =
                BASE_CONTENT_URI.buildUpon().appendPath(PATH_T1MORESDATAS).build();

        public static final String CONTENT_TYPE =
                ContentResolver.CURSOR_DIR_BASE_TYPE + "/" + CONTENT_AUTHORITY + "/" + PATH_T1MORESDATAS;

        public static final String CONTENT_ITEM_TYPE =
                ContentResolver.CURSOR_ITEM_BASE_TYPE + "/" + CONTENT_AUTHORITY + "/" + PATH_T1MORESDATAS;

        public static final String TABLE_NAME = "_t1moresdatas";
        public static final String COLUMN_NAME_MORE_TIME_LAST_MODIFIED = "_time_last_modified";
        public static final String COLUMN_NAME_MORE_COUNT = "_name_more_count";
        public static final String COLUMN_NAME_MORE_NAME = "_name_more_name";
        public static final String COLUMN_NAME_MORE_ID = "_name_more_id";
        public static final String COLUMN_NAME_MORES_PARENT_ID = "_mores_parent_id";
        public static final String COLUMN_NAME_MORE_DEPTH = "_name_more_depth";
        public static final String COLUMN_NAME_MORE_CHILDREN = "_name_more_children";
    }

}
