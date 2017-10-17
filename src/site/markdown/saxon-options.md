# Saxon configuration options for maven plugin

If you have a plugin that uses Saxon, and you want to fine configure your saxon, you can use this project.

Just declare a parameter like this :
```java
  @Parameter(name = "saxonOptions")
  private top.marchand.maven.saxon.utils.SaxonOptions saxonOptions;
```

Then, when you have build your `net.sf.saxon.s9api.Processor`, just call :
```java
  top.marchand.maven.saxon.utils.SaxonUtils.prepareSaxonConfiguration(Processor processor, saxonOptions);
```
When configuring your plugin, use this :
```xml
<saxonOptions>
  <dtd>[on,off,recover]</dtd>
  <ea>[on,off]</ea>
  <expand>[on,off]</expand>
  <ext>[on,off]</ext>
  <l>[on,off]</l>
  <m>receiver class name</m>
  <opt>see saxon documentation: http://:saxonica:com/documentation/index.html#using-xsl/commandline</opt>
  <or>OutputUriResolver class name</or>
  <outval>[recover,fatal]</outval>
  <relocate>[on,off]</relocate>
  <strip>[all,none,ignorable]</strip>
  <t>[on,off]</t>
  <TJ>[on,off]</TJ>
  <tree>[linked,tiny,tinyc]</tree>
  <val>[strict,lax]</val>
  <warnings>[silent,recover,fatal]</warnings>
  <xi>[on,off]</xi>
</saxonOptions>
```
