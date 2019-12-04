<script>
	history.pushState(null, null, window.location.href)
	window.onpopstate = function (event) {
		history.go(1);
	}
</script>