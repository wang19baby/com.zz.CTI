<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/include/taglib.jsp"%>
<!DOCTYPE html>
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
	<title>录音回放</title>
	<link rel="stylesheet" type="text/css" href="${ctxStatic}/Layui/css/Record/style.css"/>
	<script src="${ctxStatic}/Layui/js/Record/jquery-1.7.2.min.js"></script>
</head>
<body>

<div id="background"></div>
<div id="player">
	<div class="cover"></div>
	<div class="ctrl">
		<div class="tag">
			<strong>Title</strong>
			<span class="artist">Artist</span>
			<span class="album">Album</span>
		</div>
		<div class="control">
			<div class="left">
				<div class="rewind icon"></div>
				<div class="playback icon"></div>
				<div class="fastforward icon"></div>
			</div>
			<div class="volume right">
				<div class="mute icon left"></div>
				<div class="slider left">
					<div class="pace"></div>
				</div>
			</div>
		</div>
		<div class="progress">
			<div class="slider">
				<div class="loaded"></div>
				<div class="pace"></div>
			</div>
			<div class="timer left">0:00</div>
		</div>
	</div>
</div>
	<script src="${ctxStatic}/Layui/layui/layui.js"></script>
	<script src="${ctxStatic}/Layui/js/Record/jquery-ui-1.8.17.custom.min.js"></script>
	<script src="${ctxStatic}/Layui/js/Record/recordPlay.js"></script>
</body>
</html>