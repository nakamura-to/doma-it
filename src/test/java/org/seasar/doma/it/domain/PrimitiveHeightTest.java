/*
 * Copyright 2004-2010 the Seasar Foundation and the Others.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND,
 * either express or implied. See the License for the specific language
 * governing permissions and limitations under the License.
 */
package org.seasar.doma.it.domain;

import static org.junit.Assert.assertEquals;

import java.util.Optional;
import org.junit.Test;
import org.seasar.doma.it.holder.PrimitiveHeight;
import org.seasar.doma.jdbc.ConfigSupport;
import org.seasar.doma.jdbc.holder.HolderDesc;
import org.seasar.doma.jdbc.holder.HolderDescFactory;

/** @author nakamura-to */
public class PrimitiveHeightTest {

  @Test
  public void testDefaultValue() throws Exception {
    HolderDesc<Integer, PrimitiveHeight> holderDesc =
        HolderDescFactory.getHolderDesc(PrimitiveHeight.class, ConfigSupport.defaultClassHelper);
    var scalar = holderDesc.createScalar();
    var holder = scalar.get();
    assertEquals(0, holder.getValue());
  }

  @Test
  public void testDefaultValue_Optional() throws Exception {
    HolderDesc<Integer, PrimitiveHeight> holderDesc =
        HolderDescFactory.getHolderDesc(PrimitiveHeight.class, ConfigSupport.defaultClassHelper);
    var scalar = holderDesc.createOptionalScalar();
    Optional<PrimitiveHeight> optional = scalar.get();
    PrimitiveHeight domain = optional.get();
    assertEquals(0, domain.getValue());
  }
}
