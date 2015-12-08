<?xml version="1.0" encoding="ISO-8859-1"?>
<xsl:stylesheet version="1.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform">

<xsl:template match="TAXONOMY">
<html>
<head>
</head>
<body>
	<xsl:apply-templates/>
</body>
</html>
</xsl:template>

<!-- Excluded tags -->
<xsl:template match="AREA_NAME">
</xsl:template>
<xsl:template match="SHORT_NAME">
</xsl:template>
<xsl:template match="DESCRIPTION">
</xsl:template>
<xsl:template match="OBJECTIVE">
</xsl:template>
<xsl:template match="TIME">
</xsl:template>
<!-- <xsl:template match="/UNIT">
	<xsl:apply_templates/>
</xsl:template> -->

<!-- Included tags -->
<xsl:template match="UNIT_NAME">
<b>
	<xsl:apply-templates/>
</b>
</xsl:template>

<xsl:template match="TOPICS">
<ul>
	<xsl:apply-templates/>
</ul>
</xsl:template>

<xsl:template match="TOPIC">
<li>
	<xsl:apply-templates/>
</li>
</xsl:template>

</xsl:stylesheet>