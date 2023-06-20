package com.rtarita.yourbuild.model;

import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

public class ClientModel implements Model {
    public static final String CLIENT_ID = "CLIENT_ID";
    public static final String FULL_NAME = "FULL_NAME";
    public static final String DISPLAY_NAME = "DISPLAY_NAME";
    public static final String VOUCHERS = "VOUCHERS";
    public static final String INVITED_BY = "INVITED_BY";
    private final int clientId;
    private final String fullName;
    private final String displayName;
    private final int vouchers;
    @Nullable
    private final Integer invitedBy;

    private final Map<String, Object> primaryKey = new HashMap<>(1);
    private final Map<List<String>, List<Object>> foreignKeys = new HashMap<>(1);
    private final Map<String, Object> insertStatementParameters = new HashMap<>(5);

    public ClientModel(int clientId, String fullName, String displayName, int vouchers, @Nullable Integer invitedBy) {
        this.clientId = clientId;
        this.fullName = fullName;
        this.displayName = displayName;
        this.vouchers = vouchers;
        this.invitedBy = invitedBy;

        primaryKey.put(CLIENT_ID, clientId);

        List<Object> invitedByForeignKey = new ArrayList<>(1);
        invitedByForeignKey.add(invitedBy);
        foreignKeys.put(List.of(INVITED_BY), invitedByForeignKey);

        insertStatementParameters.put(CLIENT_ID, clientId);
        insertStatementParameters.put(FULL_NAME, fullName);
        insertStatementParameters.put(DISPLAY_NAME, displayName);
        insertStatementParameters.put(VOUCHERS, vouchers);
        insertStatementParameters.put(INVITED_BY, invitedBy);
    }

    public int getClientId() {
        return clientId;
    }

    public String getFullName() {
        return fullName;
    }

    public String getDisplayName() {
        return displayName;
    }

    public int getVouchers() {
        return vouchers;
    }

    @Nullable
    public Integer getInvitedBy() {
        return invitedBy;
    }

    @Override
    public Map<String, Object> primaryKey() {
        return primaryKey;
    }

    @Override
    public Map<List<String>, List<Object>> foreignKeys() {
        return foreignKeys;
    }

    @Override
    public Map<String, Object> insertStatementParameters() {
        return new HashMap<>(insertStatementParameters);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof ClientModel that)) return false;
        return clientId == that.clientId
            && vouchers == that.vouchers
            && fullName.equals(that.fullName)
            && displayName.equals(that.displayName)
            && Objects.equals(invitedBy, that.invitedBy);
    }

    @Override
    public int hashCode() {
        return Objects.hash(clientId, fullName, displayName, vouchers, invitedBy);
    }

    @Override
    public String toString() {
        return "ClientModel{" +
            "clientId=" + clientId +
            ", fullName='" + fullName + '\'' +
            ", displayName='" + displayName + '\'' +
            ", vouchers=" + vouchers +
            ", invitedBy=" + invitedBy +
            '}';
    }
}
