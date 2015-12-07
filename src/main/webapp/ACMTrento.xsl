<?xml version="1.0" encoding="ISO-8859-1"?>
<xsl:stylesheet version="1.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform">

<xsl:template match="root">
<html>
<head>
</head>
<body style="background-color:lightgreen;">
	<xsl:apply-templates/>
</body>
</html>
</xsl:template>

<xsl:template match="UNIT_NAME">
<h1><b>
	<xsl:apply-templates/>
</b></h1>
</xsl:template>

<xsl:template match="TOPICS">
<ul>
	<xsl:apply-templates/>
</ul>
</xsl:template>

<xsl:template match="TOPIC">
<li>
<i style="font-size:15pt;">
	<xsl:apply-templates/>
</i>
</li>
</xsl:template>

</xsl:stylesheet>