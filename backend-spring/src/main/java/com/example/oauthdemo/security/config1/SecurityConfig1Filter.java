package com.example.oauthdemo.security.config1;

import org.springframework.core.type.ClassMetadata;
import org.springframework.core.type.classreading.MetadataReader;
import org.springframework.core.type.classreading.MetadataReaderFactory;
import org.springframework.core.type.filter.TypeFilter;

import java.io.IOException;

public class SecurityConfig1Filter implements TypeFilter {

    @Override
    public boolean match(MetadataReader metadataReader, MetadataReaderFactory metadataReaderFactory) throws IOException {
        ClassMetadata classMetadata = metadataReader.getClassMetadata();
        String className = classMetadata.getClassName();
        if (className.startsWith("com.example.oauthdemo.security.config2") ||
                className.equals("com.example.oauthdemo.OauthdemoApplication2")) {
            return true;
        }

        return false;
    }
}
