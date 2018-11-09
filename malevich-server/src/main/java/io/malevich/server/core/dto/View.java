package io.malevich.server.core.dto;



public class View {
    public interface Anonymous {}

    public interface User extends Anonymous {}

    public interface Admin extends User {}

    public interface Gallery extends User {}

    public interface Trader extends User {}
}
