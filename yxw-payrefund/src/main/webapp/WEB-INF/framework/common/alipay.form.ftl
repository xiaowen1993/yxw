<#if params.alipayFormHtml?exists && params.alipayFormHtml??>
	${params.alipayFormHtml}
</#if>

<script>
	function pay() {
		document.forms[0].submit();
	}
</script>
