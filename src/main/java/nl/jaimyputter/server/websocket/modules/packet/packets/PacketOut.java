package nl.jaimyputter.server.websocket.modules.packet.packets;

import java.io.ByteArrayOutputStream;
import java.lang.reflect.Field;

/**
 * Created by Spraxs
 * Date: 10/29/2019
 */


public abstract class PacketOut {

    private final ByteArrayOutputStream _baos  = new ByteArrayOutputStream();

    public short id = -1;

    public abstract void onDataPrepare();

    public void handlePacketData() {
        if (id < 0) {
            throw new RuntimeException("Packet out id has not been set!");
        }

        // Set all field in correct order

        Field[] fields = getClass().getFields();

        Field idField = fields[fields.length - 1];

        fields = new Field[getClass().getFields().length];

        fields[0] = idField;

        for (int i = 1; i < fields.length; i++) {
            fields[i] = getClass().getFields()[i - 1];
        }

        for (Field field : fields) {
            try {
                Object value = field.get(this);
                writeNext(field.getType(), value);

            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        }
    }

    private void writeNext(Class<?> type, Object value) {

        if (type.equals(String.class)) {
            writeString((String) value);
            return;
        }

        if (type.equals(byte.class)) {
            writeByte((byte) value);
            return;
        }

        if (type.equals(short.class)) {
            writeShort((short) value);
            return;
        }

        if (type.equals(int.class)) {
            writeInt((int) value);
            return;
        }

        if (type.equals(long.class)) {
            writeLong((long) value);
            return;
        }

        if (type.equals(float.class)) {
            writeFloat((float) value);
            return;
        }

        if (type.equals(double.class)) {
            writeDouble((double) value);
            return;
        }

        throw new RuntimeException("Value type " + type.getSimpleName() + " is not supported by packets!");
    }

    private void writeString(String value) {
        if (value != null) {
            try {
                final byte[] byteArray = value.getBytes("UTF-8");
                writeByte(byteArray.length);
                writeBytes(byteArray);
            } catch (Exception e) {
                _baos.write(0);
            }
        } else {
            _baos.write(0);
        }
    }

    private void writeBytes(byte[] array) {
        try {
            _baos.write(array);
        } catch (Exception e) {
        }
    }

    private void writeByte(int value) {
        _baos.write(value & 0xff);
    }

    private void writeShort(int value) {

        _baos.write(value & 0xff);
        _baos.write((value >> 8) & 0xff);

    }

    private void writeInt(int value) {
        _baos.write(value & 0xff);
        _baos.write((value >> 8) & 0xff);
        _baos.write((value >> 16) & 0xff);
        _baos.write((value >> 24) & 0xff);
    }

    private void writeLong(long value) {
        _baos.write((int) (value & 0xff));
        _baos.write((int) ((value >> 8) & 0xff));
        _baos.write((int) ((value >> 16) & 0xff));
        _baos.write((int) ((value >> 24) & 0xff));
        _baos.write((int) ((value >> 32) & 0xff));
        _baos.write((int) ((value >> 40) & 0xff));
        _baos.write((int) ((value >> 48) & 0xff));
        _baos.write((int) ((value >> 56) & 0xff));
    }

    private void writeFloat(float fvalue) {
        final int value = Float.floatToRawIntBits(fvalue);
        _baos.write(value & 0xff);
        _baos.write((value >> 8) & 0xff);
        _baos.write((value >> 16) & 0xff);
        _baos.write((value >> 24) & 0xff);
    }

    private void writeDouble(double dvalue) {
        final long value = Double.doubleToRawLongBits(dvalue);
        _baos.write((int) (value & 0xff));
        _baos.write((int) ((value >> 8) & 0xff));
        _baos.write((int) ((value >> 16) & 0xff));
        _baos.write((int) ((value >> 24) & 0xff));
        _baos.write((int) ((value >> 32) & 0xff));
        _baos.write((int) ((value >> 40) & 0xff));
        _baos.write((int) ((value >> 48) & 0xff));
        _baos.write((int) ((value >> 56) & 0xff));
    }

    public byte[] getSendableBytes() {

        /* Encryption
        // Encrypt bytes.
        final byte[] encryptedBytes = Encryption.encrypt(_baos.toByteArray());

        final int size = encryptedBytes.length;

        // Create two bytes for length (short - max length 32767).
        final byte[] lengthBytes = new byte[2];
        lengthBytes[0] = (byte) (size & 0xff);
        lengthBytes[1] = (byte) ((size >> 8) & 0xff);

        // Join bytes.
        byte[] result = new byte[size + 2];
        System.arraycopy(lengthBytes, 0, result, 0, 2);
        System.arraycopy(encryptedBytes, 0, result, 2, size);


        // Return the data.
        return result;

        */

        return _baos.toByteArray();
    }
}
