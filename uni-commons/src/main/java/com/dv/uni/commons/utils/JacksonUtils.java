package com.dv.uni.commons.utils;

import com.dv.uni.commons.enums.Status;
import com.dv.uni.commons.exceptions.BaseException;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.core.*;
import com.fasterxml.jackson.core.type.ResolvedType;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.*;
import com.fasterxml.jackson.databind.type.TypeFactory;

import java.io.*;
import java.net.URL;

/**
 * @Author 张盼
 * @Email zhangpan_soft@163.com
 * @Blog zhangpan_soft
 * @Date 2020/8/2 0002
 */
public class JacksonUtils {
    private static final ObjectMapper objectMapper = new ObjectMapper();

    static {
        objectMapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
        objectMapper.configure(SerializationFeature.INDENT_OUTPUT, Boolean.TRUE);
        objectMapper.configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, true);
        objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
    }

    public static ObjectMapper getObjectMapper() {
        return objectMapper;
    }

    @SuppressWarnings("unchecked")
    public static <T> T readValue(JsonParser p, Class<T> valueType) {
        try {
            return objectMapper.readValue(p, valueType);
        } catch (Exception e) {
            e.printStackTrace();
            throw BaseException.of(Status.JACKSON_EXCEPTION, e);
        }
    }

    /**
     * Method to deserialize JSON content into a Java type, reference
     * to which is passed as argument. Type is passed using so-called
     * "super type token" (see )
     * and specifically needs to be used if the root type is a
     * parameterized (generic) container type.
     *
     * @throws IOException          if a low-level I/O problem (unexpected end-of-input,
     *                              network error) occurs (passed through as-is without additional wrapping -- note
     *                              that this is one case where {@link DeserializationFeature#WRAP_EXCEPTIONS}
     *                              does NOT result in wrapping of exception even if enabled)
     * @throws JsonParseException   if underlying input contains invalid content
     *                              of type {@link JsonParser} supports (JSON for default case)
     * @throws JsonMappingException if the input JSON structure does not match structure
     *                              expected for result type (or has other mismatch issues)
     */
    @SuppressWarnings("unchecked")
    public static <T> T readValue(JsonParser p, TypeReference<T> valueTypeRef) {
        try {
            return objectMapper.readValue(p, valueTypeRef);
        } catch (Exception e) {
            e.printStackTrace();
            throw BaseException.of(Status.JACKSON_EXCEPTION, e);
        }
    }

    /**
     * Method to deserialize JSON content into a Java type, reference
     * to which is passed as argument. Type is passed using
     * Jackson specific type; instance of which can be constructed using
     * {@link TypeFactory}.
     *
     * @throws IOException          if a low-level I/O problem (unexpected end-of-input,
     *                              network error) occurs (passed through as-is without additional wrapping -- note
     *                              that this is one case where {@link DeserializationFeature#WRAP_EXCEPTIONS}
     *                              does NOT result in wrapping of exception even if enabled)
     * @throws JsonParseException   if underlying input contains invalid content
     *                              of type {@link JsonParser} supports (JSON for default case)
     * @throws JsonMappingException if the input JSON structure does not match structure
     *                              expected for result type (or has other mismatch issues)
     */
    @SuppressWarnings("unchecked")
    public static final <T> T readValue(JsonParser p, ResolvedType valueType) {
        try {
            return objectMapper.readValue(p, valueType);
        } catch (Exception e) {
            e.printStackTrace();
            throw BaseException.of(Status.JACKSON_EXCEPTION, e);
        }
    }

    /**
     * Type-safe overloaded method, basically alias for {@link #readValue(JsonParser, Class)}.
     *
     * @throws IOException          if a low-level I/O problem (unexpected end-of-input,
     *                              network error) occurs (passed through as-is without additional wrapping -- note
     *                              that this is one case where {@link DeserializationFeature#WRAP_EXCEPTIONS}
     *                              does NOT result in wrapping of exception even if enabled)
     * @throws JsonParseException   if underlying input contains invalid content
     *                              of type {@link JsonParser} supports (JSON for default case)
     * @throws JsonMappingException if the input JSON structure does not match structure
     *                              expected for result type (or has other mismatch issues)
     */
    @SuppressWarnings("unchecked")
    public static <T> T readValue(JsonParser p, JavaType valueType) {
        try {
            return objectMapper.readValue(p, valueType);
        } catch (Exception e) {
            e.printStackTrace();
            throw BaseException.of(Status.JACKSON_EXCEPTION, e);
        }
    }

    /**
     * Method to deserialize JSON content as a tree {@link JsonNode}.
     * Returns {@link JsonNode} that represents the root of the resulting tree, if there
     * was content to read, or {@code null} if no more content is accessible
     * via passed {@link JsonParser}.
     * <p>
     * NOTE! Behavior with end-of-input (no more content) differs between this
     * {@code readTree} method, and all other methods that take input source: latter
     * will return "missing node", NOT {@code null}
     *
     * @return a {@link JsonNode}, if valid JSON content found; null
     * if input has no content to bind -- note, however, that if
     * JSON <code>null</code> token is found, it will be represented
     * as a non-null {@link JsonNode} (one that returns <code>true</code>
     * for {@link JsonNode#isNull()}
     * @throws IOException        if a low-level I/O problem (unexpected end-of-input,
     *                            network error) occurs (passed through as-is without additional wrapping -- note
     *                            that this is one case where {@link DeserializationFeature#WRAP_EXCEPTIONS}
     *                            does NOT result in wrapping of exception even if enabled)
     * @throws JsonParseException if underlying input contains invalid content
     *                            of type {@link JsonParser} supports (JSON for default case)
     */
    public static <T extends TreeNode> T readTree(JsonParser p) {
        try {
            return objectMapper.readTree(p);
        } catch (Exception e) {
            e.printStackTrace();
            throw BaseException.of(Status.JACKSON_EXCEPTION, e);
        }
    }

    /**
     * Convenience method, equivalent in function to:
     * <pre>
     *   readerFor(valueType).readValues(p);
     * </pre>
     * <p>
     * Method for reading sequence of Objects from parser stream.
     * Sequence can be either root-level "unwrapped" sequence (without surrounding
     * JSON array), or a sequence contained in a JSON Array.
     * In either case {@link JsonParser} <b>MUST</b> point to the first token of
     * the first element, OR not point to any token (in which case it is advanced
     * to the next token). This means, specifically, that for wrapped sequences,
     * parser MUST NOT point to the surrounding <code>START_ARRAY</code> (one that
     * contains values to read) but rather to the token following it which is the first
     * token of the first value to read.
     * <p>
     * Note that {@link ObjectReader} has more complete set of variants.
     */
    public static <T> MappingIterator<T> readValues(JsonParser p, ResolvedType valueType) {
        try {
            return objectMapper.readValues(p, valueType);
        } catch (Exception e) {
            e.printStackTrace();
            throw BaseException.of(Status.JACKSON_EXCEPTION, e);
        }
    }

    /**
     * Convenience method, equivalent in function to:
     * <pre>
     *   readerFor(valueType).readValues(p);
     * </pre>
     * <p>
     * Type-safe overload of {@link #readValues(JsonParser, ResolvedType)}.
     */
    public static <T> MappingIterator<T> readValues(JsonParser p, JavaType valueType) {
        try {
            return objectMapper.readValues(p, valueType);
        } catch (Exception e) {
            e.printStackTrace();
            throw BaseException.of(Status.JACKSON_EXCEPTION, e);
        }
    }

    /**
     * Convenience method, equivalent in function to:
     * <pre>
     *   readerFor(valueType).readValues(p);
     * </pre>
     * <p>
     * Type-safe overload of {@link #readValues(JsonParser, ResolvedType)}.
     */
    public static <T> MappingIterator<T> readValues(JsonParser p, Class<T> valueType) {
        try {
            return objectMapper.readValues(p, valueType);
        } catch (Exception e) {
            e.printStackTrace();
            throw BaseException.of(Status.JACKSON_EXCEPTION, e);
        }
    }

    /**
     * Method for reading sequence of Objects from parser stream.
     */
    public static <T> MappingIterator<T> readValues(JsonParser p, TypeReference<T> valueTypeRef) {
        try {
            return objectMapper.readValues(p, valueTypeRef);
        } catch (Exception e) {
            e.printStackTrace();
            throw BaseException.of(Status.JACKSON_EXCEPTION, e);
        }
    }


    /**
     * Method to deserialize JSON content as tree expressed
     * using set of {@link JsonNode} instances.
     * Returns root of the resulting tree (where root can consist
     * of just a single node if the current event is a
     * value event, not container).
     * <p>
     * If a low-level I/O problem (missing input, network error) occurs,
     * a {@link IOException} will be thrown.
     * If a parsing problem occurs (invalid JSON),
     * {@link JsonParseException} will be thrown.
     * If no content is found from input (end-of-input), Java
     * <code>null</code> will be returned.
     *
     * @param in Input stream used to read JSON content
     *           for building the JSON tree.
     * @return a {@link JsonNode}, if valid JSON content found; null
     * if input has no content to bind -- note, however, that if
     * JSON <code>null</code> token is found, it will be represented
     * as a non-null {@link JsonNode} (one that returns <code>true</code>
     * for {@link JsonNode#isNull()}
     * @throws JsonParseException if underlying input contains invalid content
     *                            of type {@link JsonParser} supports (JSON for default case)
     */
    public static JsonNode readTree(InputStream in) {
        try {
            return objectMapper.readTree(in);
        } catch (Exception e) {
            e.printStackTrace();
            throw BaseException.of(Status.JACKSON_EXCEPTION, e);
        }
    }

    /**
     * Same as {@link #readTree(InputStream)} except content accessed through
     * passed-in {@link Reader}
     */
    public static JsonNode readTree(Reader r) {
        try {
            return objectMapper.readTree(r);
        } catch (Exception e) {
            e.printStackTrace();
            throw BaseException.of(Status.JACKSON_EXCEPTION, e);
        }
    }

    /**
     * Same as {@link #readTree(InputStream)} except content read from
     * passed-in {@link String}
     */
    public static JsonNode readTree(String content) {
        try {
            return objectMapper.readTree(content);
        } catch (Exception e) {
            e.printStackTrace();
            throw BaseException.of(Status.JACKSON_EXCEPTION, e);
        }
    }

    /**
     * Same as {@link #readTree(InputStream)} except content read from
     * passed-in byte array.
     */
    public static JsonNode readTree(byte[] content) {
        try {
            return objectMapper.readTree(content);
        } catch (Exception e) {
            e.printStackTrace();
            throw BaseException.of(Status.JACKSON_EXCEPTION, e);
        }
    }

    /**
     * Same as {@link #readTree(InputStream)} except content read from
     * passed-in byte array.
     */
    public static JsonNode readTree(byte[] content, int offset, int len) {
        try {
            return objectMapper.readTree(content, offset, len);
        } catch (Exception e) {
            e.printStackTrace();
            throw BaseException.of(Status.JACKSON_EXCEPTION, e);
        }
    }

    /**
     * Same as {@link #readTree(InputStream)} except content read from
     * passed-in {@link File}.
     */
    public static JsonNode readTree(File file) {
        try {
            return objectMapper.readTree(file);
        } catch (Exception e) {
            e.printStackTrace();
            throw BaseException.of(Status.JACKSON_EXCEPTION, e);
        }
    }

    /**
     * Same as {@link #readTree(InputStream)} except content read from
     * passed-in {@link URL}.
     * <p>
     * NOTE: handling of {@link URL} is delegated to
     * {@link JsonFactory#createParser(URL)} and usually simply
     * calls {@link URL#openStream()}, meaning no special handling
     * is done. If different HTTP connection options are needed you will need
     * to create {@link InputStream} separately.
     */
    public static JsonNode readTree(URL source) {
        try {
            return objectMapper.readTree(source);
        } catch (Exception e) {
            e.printStackTrace();
            throw BaseException.of(Status.JACKSON_EXCEPTION, e);
        }
    }

    /**
     * Method that can be used to serialize any Java value as
     * JSON output, using provided {@link JsonGenerator}.
     */
    public static void writeValue(JsonGenerator g, Object value) {
        try {
            objectMapper.writeValue(g, value);
        } catch (Exception e) {
            e.printStackTrace();
            throw BaseException.of(Status.JACKSON_EXCEPTION, e);
        }
    }

    public static void writeTree(JsonGenerator g, TreeNode rootNode) {
        try {
            objectMapper.writeTree(g, rootNode);
        } catch (Exception e) {
            e.printStackTrace();
            throw BaseException.of(Status.JACKSON_EXCEPTION, e);
        }
    }

    /**
     * Method to serialize given JSON Tree, using generator
     * provided.
     */
    public static void writeTree(JsonGenerator g, JsonNode rootNode) {
        try {
            objectMapper.writeTree(g, rootNode);
        } catch (Exception e) {
            e.printStackTrace();
            throw BaseException.of(Status.JACKSON_EXCEPTION, e);
        }
    }


    /**
     * Convenience conversion method that will bind data given JSON tree
     * contains into specific value (usually bean) type.
     * <p>
     * Functionally equivalent to:
     * <pre>
     *   objectMapper.convertValue(n, valueClass);
     * </pre>
     */
    @SuppressWarnings("unchecked")
    public static <T> T treeToValue(TreeNode n, Class<T> valueType) {
        try {
            return objectMapper.treeToValue(n, valueType);
        } catch (Exception e) {
            e.printStackTrace();
            throw BaseException.of(Status.JACKSON_EXCEPTION, e);
        }
    }

    /**
     * Reverse of {@link #treeToValue}; given a value (usually bean), will
     * construct equivalent JSON Tree representation. Functionally similar
     * to serializing value into JSON and parsing JSON as tree, but
     * more efficient.
     * <p>
     * NOTE: while results are usually identical to that of serialization followed
     * by deserialization, this is not always the case. In some cases serialization
     * into intermediate representation will retain encapsulation of things like
     * raw value ({@link com.fasterxml.jackson.databind.util.RawValue}) or basic
     * node identity ({@link JsonNode}). If so, result is a valid tree, but values
     * are not re-constructed through actual JSON representation. So if transformation
     * requires actual materialization of JSON (or other data format that this mapper
     * produces), it will be necessary to do actual serialization.
     *
     * @param <T>       Actual node type; usually either basic {@link JsonNode} or
     *                  {@link com.fasterxml.jackson.databind.node.ObjectNode}
     * @param fromValue Bean value to convert
     * @return (non - null) Root node of the resulting JSON tree: in case of {@code null} value,
     * node for which {@link JsonNode#isNull()} returns {@code true}.
     */
    @SuppressWarnings({
            "unchecked",
            "resource"
    })
    public static <T extends JsonNode> T valueToTree(Object fromValue) {
        try {
            return objectMapper.valueToTree(fromValue);
        } catch (Exception e) {
            e.printStackTrace();
            throw BaseException.of(Status.JACKSON_EXCEPTION, e);
        }
    }

    /**
     * Method to deserialize JSON content from given file into given Java type.
     *
     * @throws IOException          if a low-level I/O problem (unexpected end-of-input,
     *                              network error) occurs (passed through as-is without additional wrapping -- note
     *                              that this is one case where {@link DeserializationFeature#WRAP_EXCEPTIONS}
     *                              does NOT result in wrapping of exception even if enabled)
     * @throws JsonParseException   if underlying input contains invalid content
     *                              of type {@link JsonParser} supports (JSON for default case)
     * @throws JsonMappingException if the input JSON structure does not match structure
     *                              expected for result type (or has other mismatch issues)
     */
    @SuppressWarnings("unchecked")
    public static <T> T readValue(File src, Class<T> valueType) {
        try {
            return objectMapper.readValue(src, valueType);
        } catch (Exception e) {
            e.printStackTrace();
            throw BaseException.of(Status.JACKSON_EXCEPTION, e);
        }
    }

    /**
     * Method to deserialize JSON content from given file into given Java type.
     *
     * @throws IOException          if a low-level I/O problem (unexpected end-of-input,
     *                              network error) occurs (passed through as-is without additional wrapping -- note
     *                              that this is one case where {@link DeserializationFeature#WRAP_EXCEPTIONS}
     *                              does NOT result in wrapping of exception even if enabled)
     * @throws JsonParseException   if underlying input contains invalid content
     *                              of type {@link JsonParser} supports (JSON for default case)
     * @throws JsonMappingException if the input JSON structure does not match structure
     *                              expected for result type (or has other mismatch issues)
     */
    @SuppressWarnings({"unchecked"})
    public static <T> T readValue(File src, TypeReference<T> valueTypeRef) {
        try {
            return objectMapper.readValue(src, valueTypeRef);
        } catch (Exception e) {
            e.printStackTrace();
            throw BaseException.of(Status.JACKSON_EXCEPTION, e);
        }
    }

    /**
     * Method to deserialize JSON content from given file into given Java type.
     *
     * @throws IOException          if a low-level I/O problem (unexpected end-of-input,
     *                              network error) occurs (passed through as-is without additional wrapping -- note
     *                              that this is one case where {@link DeserializationFeature#WRAP_EXCEPTIONS}
     *                              does NOT result in wrapping of exception even if enabled)
     * @throws JsonParseException   if underlying input contains invalid content
     *                              of type {@link JsonParser} supports (JSON for default case)
     * @throws JsonMappingException if the input JSON structure does not match structure
     *                              expected for result type (or has other mismatch issues)
     */
    @SuppressWarnings("unchecked")
    public static <T> T readValue(File src, JavaType valueType) {
        try {
            return objectMapper.readValue(src, valueType);
        } catch (Exception e) {
            e.printStackTrace();
            throw BaseException.of(Status.JACKSON_EXCEPTION, e);
        }
    }

    /**
     * Method to deserialize JSON content from given resource into given Java type.
     * <p>
     * NOTE: handling of {@link URL} is delegated to
     * {@link JsonFactory#createParser(URL)} and usually simply
     * calls {@link URL#openStream()}, meaning no special handling
     * is done. If different HTTP connection options are needed you will need
     * to create {@link InputStream} separately.
     *
     * @throws IOException          if a low-level I/O problem (unexpected end-of-input,
     *                              network error) occurs (passed through as-is without additional wrapping -- note
     *                              that this is one case where {@link DeserializationFeature#WRAP_EXCEPTIONS}
     *                              does NOT result in wrapping of exception even if enabled)
     * @throws JsonParseException   if underlying input contains invalid content
     *                              of type {@link JsonParser} supports (JSON for default case)
     * @throws JsonMappingException if the input JSON structure does not match structure
     *                              expected for result type (or has other mismatch issues)
     */
    @SuppressWarnings("unchecked")
    public static <T> T readValue(URL src, Class<T> valueType) {
        try {
            return objectMapper.readValue(src, valueType);
        } catch (Exception e) {
            e.printStackTrace();
            throw BaseException.of(Status.JACKSON_EXCEPTION, e);
        }
    }

    /**
     * Same as {@link #readValue(URL, Class)} except that target specified by {@link TypeReference}.
     */
    @SuppressWarnings({"unchecked"})
    public static <T> T readValue(URL src, TypeReference<T> valueTypeRef) {
        try {
            return objectMapper.readValue(src, valueTypeRef);
        } catch (Exception e) {
            e.printStackTrace();
            throw BaseException.of(Status.JACKSON_EXCEPTION, e);
        }
    }

    /**
     * Same as {@link #readValue(URL, Class)} except that target specified by {@link JavaType}.
     */
    @SuppressWarnings("unchecked")
    public static <T> T readValue(URL src, JavaType valueType) {
        try {
            return objectMapper.readValue(src, valueType);
        } catch (Exception e) {
            e.printStackTrace();
            throw BaseException.of(Status.JACKSON_EXCEPTION, e);
        }
    }

    /**
     * Method to deserialize JSON content from given JSON content String.
     *
     * @throws JsonParseException   if underlying input contains invalid content
     *                              of type {@link JsonParser} supports (JSON for default case)
     * @throws JsonMappingException if the input JSON structure does not match structure
     *                              expected for result type (or has other mismatch issues)
     */
    public static <T> T readValue(String content, Class<T> valueType) {
        try {
            return objectMapper.readValue(content, valueType);
        } catch (Exception e) {
            e.printStackTrace();
            throw BaseException.of(Status.JACKSON_EXCEPTION, e);
        }
    }

    /**
     * Method to deserialize JSON content from given JSON content String.
     *
     * @throws JsonParseException   if underlying input contains invalid content
     *                              of type {@link JsonParser} supports (JSON for default case)
     * @throws JsonMappingException if the input JSON structure does not match structure
     *                              expected for result type (or has other mismatch issues)
     */
    public static <T> T readValue(String content, TypeReference<T> valueTypeRef) {
        try {
            return objectMapper.readValue(content, valueTypeRef);
        } catch (Exception e) {
            e.printStackTrace();
            throw BaseException.of(Status.JACKSON_EXCEPTION, e);
        }
    }

    /**
     * Method to deserialize JSON content from given JSON content String.
     *
     * @throws JsonParseException   if underlying input contains invalid content
     *                              of type {@link JsonParser} supports (JSON for default case)
     * @throws JsonMappingException if the input JSON structure does not match structure
     *                              expected for result type (or has other mismatch issues)
     */
    @SuppressWarnings("unchecked")
    public static <T> T readValue(String content, JavaType valueType) {
        try {
            return objectMapper.readValue(content, valueType);
        } catch (Exception e) {
            e.printStackTrace();
            throw BaseException.of(Status.JACKSON_EXCEPTION, e);
        }
    }

    @SuppressWarnings("unchecked")
    public static <T> T readValue(Reader src, Class<T> valueType) {
        try {
            return objectMapper.readValue(src, valueType);
        } catch (Exception e) {
            e.printStackTrace();
            throw BaseException.of(Status.JACKSON_EXCEPTION, e);
        }
    }

    @SuppressWarnings({"unchecked"})
    public static <T> T readValue(Reader src, TypeReference<T> valueTypeRef) {
        try {
            return objectMapper.readValue(src, valueTypeRef);
        } catch (Exception e) {
            e.printStackTrace();
            throw BaseException.of(Status.JACKSON_EXCEPTION, e);
        }
    }

    @SuppressWarnings("unchecked")
    public static <T> T readValue(Reader src, JavaType valueType) {
        try {
            return objectMapper.readValue(src, valueType);
        } catch (Exception e) {
            e.printStackTrace();
            throw BaseException.of(Status.JACKSON_EXCEPTION, e);
        }
    }

    @SuppressWarnings("unchecked")
    public static <T> T readValue(InputStream src, Class<T> valueType) {
        try {
            return objectMapper.readValue(src, valueType);
        } catch (Exception e) {
            e.printStackTrace();
            throw BaseException.of(Status.JACKSON_EXCEPTION, e);
        }
    }

    @SuppressWarnings({"unchecked"})
    public static <T> T readValue(InputStream src, TypeReference<T> valueTypeRef) {
        try {
            return objectMapper.readValue(src, valueTypeRef);
        } catch (Exception e) {
            e.printStackTrace();
            throw BaseException.of(Status.JACKSON_EXCEPTION, e);
        }
    }

    @SuppressWarnings("unchecked")
    public static <T> T readValue(InputStream src, JavaType valueType) {
        try {
            return objectMapper.readValue(src, valueType);
        } catch (Exception e) {
            e.printStackTrace();
            throw BaseException.of(Status.JACKSON_EXCEPTION, e);
        }
    }

    @SuppressWarnings("unchecked")
    public static <T> T readValue(byte[] src, Class<T> valueType) {
        try {
            return objectMapper.readValue(src, valueType);
        } catch (Exception e) {
            e.printStackTrace();
            throw BaseException.of(Status.JACKSON_EXCEPTION, e);
        }
    }

    @SuppressWarnings("unchecked")
    public static <T> T readValue(byte[] src, int offset, int len, Class<T> valueType) {
        try {
            return objectMapper.readValue(src, offset, len, valueType);
        } catch (Exception e) {
            e.printStackTrace();
            throw BaseException.of(Status.JACKSON_EXCEPTION, e);
        }
    }

    @SuppressWarnings({"unchecked"})
    public static <T> T readValue(byte[] src, TypeReference<T> valueTypeRef) {
        try {
            return objectMapper.readValue(src, valueTypeRef);
        } catch (Exception e) {
            e.printStackTrace();
            throw BaseException.of(Status.JACKSON_EXCEPTION, e);
        }
    }

    @SuppressWarnings({"unchecked"})
    public static <T> T readValue(byte[] src, int offset, int len, TypeReference<T> valueTypeRef) {
        try {
            return objectMapper.readValue(src, offset, len, valueTypeRef);
        } catch (Exception e) {
            e.printStackTrace();
            throw BaseException.of(Status.JACKSON_EXCEPTION, e);
        }
    }

    @SuppressWarnings("unchecked")
    public static <T> T readValue(byte[] src, JavaType valueType) {
        try {
            return objectMapper.readValue(src, valueType);
        } catch (Exception e) {
            e.printStackTrace();
            throw BaseException.of(Status.JACKSON_EXCEPTION, e);
        }
    }

    @SuppressWarnings("unchecked")
    public static <T> T readValue(byte[] src, int offset, int len, JavaType valueType) {
        try {
            return objectMapper.readValue(src, offset, len, valueType);
        } catch (Exception e) {
            e.printStackTrace();
            throw BaseException.of(Status.JACKSON_EXCEPTION, e);
        }
    }

    @SuppressWarnings("unchecked")
    public static <T> T readValue(DataInput src, Class<T> valueType) {
        try {
            return objectMapper.readValue(src, valueType);
        } catch (Exception e) {
            e.printStackTrace();
            throw BaseException.of(Status.JACKSON_EXCEPTION, e);
        }
    }

    @SuppressWarnings("unchecked")
    public static <T> T readValue(DataInput src, JavaType valueType) {
        try {
            return objectMapper.readValue(src, valueType);
        } catch (Exception e) {
            e.printStackTrace();
            throw BaseException.of(Status.JACKSON_EXCEPTION, e);
        }
    }

    /**
     * Method that can be used to serialize any Java value as
     * JSON output, written to File provided.
     */
    public static void writeValue(File resultFile, Object value) {
        try {
            objectMapper.writeValue(resultFile, value);
        } catch (Exception e) {
            e.printStackTrace();
            throw BaseException.of(Status.JACKSON_EXCEPTION, e);
        }
    }

    /**
     * Method that can be used to serialize any Java value as
     * JSON output, using output stream provided (using encoding
     * {@link JsonEncoding#UTF8}).
     * <p>
     * Note: method does not close the underlying stream explicitly
     * here; however, {@link JsonFactory} this mapper uses may choose
     * to close the stream depending on its settings (by default,
     * it will try to close it when {@link JsonGenerator} we construct
     * is closed).
     */
    public static void writeValue(OutputStream out, Object value) {
        try {
            objectMapper.writeValue(out, value);
        } catch (Exception e) {
            e.printStackTrace();
            throw BaseException.of(Status.JACKSON_EXCEPTION, e);
        }
    }

    /**
     * @since 2.8
     */
    public static void writeValue(DataOutput out, Object value) {
        try {
            objectMapper.writeValue(out, value);
        } catch (Exception e) {
            e.printStackTrace();
            throw BaseException.of(Status.JACKSON_EXCEPTION, e);
        }
    }

    /**
     * Method that can be used to serialize any Java value as
     * JSON output, using Writer provided.
     * <p>
     * Note: method does not close the underlying stream explicitly
     * here; however, {@link JsonFactory} this mapper uses may choose
     * to close the stream depending on its settings (by default,
     * it will try to close it when {@link JsonGenerator} we construct
     * is closed).
     */
    public static void writeValue(Writer w, Object value) {
        try {
            objectMapper.writeValue(w, value);
        } catch (Exception e) {
            e.printStackTrace();
            throw BaseException.of(Status.JACKSON_EXCEPTION, e);
        }
    }

    /**
     * Method that can be used to serialize any Java value as
     * a String. Functionally equivalent to calling
     * {@link #writeValue(Writer, Object)} with {@link StringWriter}
     * and constructing String, but more efficient.
     * <p>
     * Note: prior to version 2.1, throws clause included {@link IOException}; 2.1 removed it.
     */
    @SuppressWarnings("resource")
    public static String writeValueAsString(Object value) {
        try {
            return objectMapper.writeValueAsString(value);
        } catch (Exception e) {
            e.printStackTrace();
            throw BaseException.of(Status.JACKSON_EXCEPTION, e);
        }
    }

    /**
     * Method that can be used to serialize any Java value as
     * a byte array. Functionally equivalent to calling
     * {@link #writeValue(Writer, Object)} with {@link ByteArrayOutputStream}
     * and getting bytes, but more efficient.
     * Encoding used will be UTF-8.
     * <p>
     * Note: prior to version 2.1, throws clause included {@link IOException}; 2.1 removed it.
     */
    @SuppressWarnings("resource")
    public static byte[] writeValueAsBytes(Object value) {
        try {
            return objectMapper.writeValueAsBytes(value);
        } catch (Exception e) {
            e.printStackTrace();
            throw BaseException.of(Status.JACKSON_EXCEPTION, e);
        }
    }

    /**
     * Convenience method for doing two-step conversion from given value, into
     * instance of given value type, by writing value into temporary buffer
     * and reading from the buffer into specified target type.
     * <p>
     * This method is functionally similar to first
     * serializing given value into JSON, and then binding JSON data into value
     * of given type, but should be more efficient since full serialization does
     * not (need to) occur.
     * However, same converters (serializers, deserializers) will be used as for
     * data binding, meaning same object mapper configuration works.
     * <p>
     * Note that behavior changed slightly between Jackson 2.9 and 2.10 so that
     * whereas earlier some optimizations were used to avoid write/read cycle
     * in case input was of target type, from 2.10 onwards full processing is
     * always performed. See
     * <a href="https://github.com/FasterXML/jackson-databind/issues/2220">databind#2220</a>
     * for full details of the change.
     * <p>
     * Further note that it is possible that in some cases behavior does differ
     * from full serialize-then-deserialize cycle: in most case differences are
     * unintentional (that is, flaws to fix) and should be reported, but
     * the behavior is not guaranteed to be 100% the same:
     * the goal is to allow efficient value conversions for structurally
     * compatible Objects, according to standard Jackson configuration.
     * <p>
     * Finally, this functionality is not designed to support "advanced" use
     * cases, such as conversion of polymorphic values, or cases where Object Identity
     * is used.
     *
     * @throws IllegalArgumentException If conversion fails due to incompatible type;
     *                                  if so, root cause will contain underlying checked exception data binding
     *                                  functionality threw
     */
    @SuppressWarnings("unchecked")
    public static <T> T convertValue(Object fromValue, Class<T> toValueType) {
        try {
            return objectMapper.convertValue(fromValue, toValueType);
        } catch (Exception e) {
            e.printStackTrace();
            throw BaseException.of(Status.JACKSON_EXCEPTION, e);
        }
    }

    /**
     * See {@link #convertValue(Object, Class)}
     */
    @SuppressWarnings("unchecked")
    public static <T> T convertValue(Object fromValue, TypeReference<T> toValueTypeRef) {
        try {
            return objectMapper.convertValue(fromValue, toValueTypeRef);
        } catch (Exception e) {
            e.printStackTrace();
            throw BaseException.of(Status.JACKSON_EXCEPTION, e);
        }
    }

    /**
     * See {@link #convertValue(Object, Class)}
     */
    @SuppressWarnings("unchecked")
    public static <T> T convertValue(Object fromValue, JavaType toValueType) {
        try {
            return objectMapper.convertValue(fromValue, toValueType);
        } catch (Exception e) {
            e.printStackTrace();
            throw BaseException.of(Status.JACKSON_EXCEPTION, e);
        }
    }
}
