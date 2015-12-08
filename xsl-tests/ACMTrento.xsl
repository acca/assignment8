<xsl:stylesheet xmlns:xsl="http://www.w3.org/1999/XSL/Transform" version="1.0">
	<xsl:output omit-xml-declaration="yes" indent="yes"/>
<xsl:template match="UNIT@ID=">
	<xsl:apply-templates/>
</xsl:template>
</xsl:stylesheet>
