/**
 * Copyright © 2017, Christophe Marchand
 * All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions are met:
 *     * Redistributions of source code must retain the above copyright
 *       notice, this list of conditions and the following disclaimer.
 *     * Redistributions in binary form must reproduce the above copyright
 *       notice, this list of conditions and the following disclaimer in the
 *       documentation and/or other materials provided with the distribution.
 *     * Neither the name of the <organization> nor the
 *       names of its contributors may be used to endorse or promote products
 *       derived from this software without specific prior written permission.
 *
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS" AND
 * ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED
 * WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE
 * DISCLAIMED. IN NO EVENT SHALL <COPYRIGHT HOLDER> BE LIABLE FOR ANY
 * DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES
 * (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES;
 * LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND
 * ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT
 * (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS
 * SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 */
package top.marchand.maven.saxon.utils;

import net.sf.saxon.Configuration;
import net.sf.saxon.event.Builder;
import net.sf.saxon.lib.FeatureKeys;
import net.sf.saxon.lib.Validation;
import net.sf.saxon.s9api.*;
import net.sf.saxon.trans.XPathException;

/**
 * Utility class to set parameters on saxon configuration classes
 *
 * @author <a href="mailto:christophe@marchand.top">Christophe Marchand</a>
 */
public class SaxonUtils {
    
    /**
     * Set the configuration on <tt>Processor</tt> instance.
     * @param processor The processor to configure
     * @param saxonOptions The options to set
     * @throws XPathException If a problem occurs
     */
    public static void prepareSaxonConfiguration(Processor processor, final SaxonOptions saxonOptions) throws XPathException {
        Configuration config = processor.getUnderlyingConfiguration();
        if(saxonOptions!=null) {
            if(saxonOptions.getXi()!=null) {
                processor.setConfigurationProperty(FeatureKeys.XINCLUDE, "on".equals(saxonOptions.getXi()));
            }

            String value = saxonOptions.getWarnings();
            if(value!=null) {
                if ("silent".equals(value)) {
                    processor.setConfigurationProperty(FeatureKeys.RECOVERY_POLICY, Configuration.RECOVER_SILENTLY);
                } else if ("recover".equals(value)) {
                    processor.setConfigurationProperty(FeatureKeys.RECOVERY_POLICY, Configuration.RECOVER_WITH_WARNINGS);
                } else if ("fatal".equals(value)) {
                    processor.setConfigurationProperty(FeatureKeys.RECOVERY_POLICY, Configuration.DO_NOT_RECOVER);
                }
            }
            
            value = saxonOptions.getVal();
            if(value!=null) {
                if ("strict".equals(value)) {
                    processor.setConfigurationProperty(FeatureKeys.SCHEMA_VALIDATION, Validation.STRICT);
                } else if ("lax".equals(value)) {
                    processor.setConfigurationProperty(FeatureKeys.SCHEMA_VALIDATION, Validation.LAX);
                }
            }
            
            value = saxonOptions.getTree();
            if(value!=null) {
                if ("linked".equals(value)) {
                    config.setTreeModel(Builder.LINKED_TREE);
                } else if ("tiny".equals(value)) {
                    config.setTreeModel(Builder.TINY_TREE);
                } else if ("tinyc".equals(value)) {
                    config.setTreeModel(Builder.TINY_TREE_CONDENSED);
                }
            }
            
            value = saxonOptions.getCollectionFinderClass();
            if (value != null) {
                Object resolver = config.getInstance(value, null);
                processor.setConfigurationProperty(FeatureKeys.COLLECTION_FINDER, resolver);
            }
            
            value = saxonOptions.getDtd();
            if (value != null) {
                if ("on".equals(value)) {
                    config.getParseOptions().setDTDValidationMode(Validation.STRICT);
                } else if ("off".equals(value)) {
                    config.getParseOptions().setDTDValidationMode(Validation.SKIP);
                } else if ("recover".equals(value)) {
                    config.getParseOptions().setDTDValidationMode(Validation.LAX);
                }
            }
            
            value = saxonOptions.getEa();
            if (value != null) {
                config.getDefaultXsltCompilerInfo().setAssertionsEnabled("on".equals(value));
            }
            
            value = saxonOptions.getExpand();
            if (value != null) {
                config.getParseOptions().setExpandAttributeDefaults("on".equals(value));
            }
            
            value = saxonOptions.getExt();
            if (value != null) {
                config.setBooleanProperty(FeatureKeys.ALLOW_EXTERNAL_FUNCTIONS, "on".equals(value));
            }
            
            value = saxonOptions.getL();
            if (value != null) {
                config.setBooleanProperty(FeatureKeys.LINE_NUMBERING, "on".equals(value));
            }
            
            value = saxonOptions.getM();
            if (value != null) {
                config.setConfigurationProperty(FeatureKeys.MESSAGE_EMITTER_CLASS, value);
            }
            
            value = saxonOptions.getOpt();
            if (value != null) {
                config.setConfigurationProperty(FeatureKeys.OPTIMIZATION_LEVEL, value);
            }
            
            value = saxonOptions.getOr();
            if (value != null) {
                Object resolver = config.getInstance(value, null);
                config.setConfigurationProperty(FeatureKeys.OUTPUT_URI_RESOLVER, resolver);
            }
            value = saxonOptions.getR();
            if (value != null) {
                config.setConfigurationProperty(FeatureKeys.URI_RESOLVER_CLASS, value);
            }
            // TODO : relocate : this applies only to the compiler
            value = saxonOptions.getOutval();
            if (value != null) {
                Boolean isRecover = "recover".equals(value);
                config.setConfigurationProperty(FeatureKeys.VALIDATION_WARNINGS, isRecover);
                config.setConfigurationProperty(FeatureKeys.VALIDATION_COMMENTS, isRecover);
            }
            
            value = saxonOptions.getStrip();
            if (value != null) {
                config.setConfigurationProperty(FeatureKeys.STRIP_WHITESPACE, value);
            }
            
            value = saxonOptions.getT();
            if (value != null) {
                config.setCompileWithTracing(true);
            }
            
            value = saxonOptions.getTJ();
            if (value != null) {
                config.setBooleanProperty(FeatureKeys.TRACE_EXTERNAL_FUNCTIONS,"on".equals(value));
            }
        }
    }
    /**
     * Set the configuration on <tt>XsltCompiler</tt> instance.
     * <strong>Warning</strong>: the <tt>compiler</tt> should have been created from a <tt>processor</tt> that
     * has been configured via {@link #prepareSaxonConfiguration(net.sf.saxon.s9api.Processor, top.marchand.maven.saxon.utils.SaxonOptions)}.
     * @param compiler The compiler to configure
     * @param saxonOptions The options
     * @throws XPathException If a problem occurs
     */
    public static void configureXsltCompiler(XsltCompiler compiler, final SaxonOptions saxonOptions) throws XPathException {
        if(saxonOptions.getRelocate()!=null) {
            compiler.setRelocatable("on".equals(saxonOptions.getRelocate()));
        }
    }
}
