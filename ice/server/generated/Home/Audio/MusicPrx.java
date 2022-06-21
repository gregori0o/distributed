//
// Copyright (c) ZeroC, Inc. All rights reserved.
//
//
// Ice version 3.7.3
//
// <auto-generated>
//
// Generated from file `smart_devices.ice'
//
// Warning: do not edit this file.
//
// </auto-generated>
//

package Home.Audio;

public interface MusicPrx extends Home.DevicePrx
{
    default void volumeUp()
    {
        volumeUp(com.zeroc.Ice.ObjectPrx.noExplicitContext);
    }

    default void volumeUp(java.util.Map<String, String> context)
    {
        _iceI_volumeUpAsync(context, true).waitForResponse();
    }

    default java.util.concurrent.CompletableFuture<Void> volumeUpAsync()
    {
        return _iceI_volumeUpAsync(com.zeroc.Ice.ObjectPrx.noExplicitContext, false);
    }

    default java.util.concurrent.CompletableFuture<Void> volumeUpAsync(java.util.Map<String, String> context)
    {
        return _iceI_volumeUpAsync(context, false);
    }

    /**
     * @hidden
     * @param context -
     * @param sync -
     * @return -
     **/
    default com.zeroc.IceInternal.OutgoingAsync<Void> _iceI_volumeUpAsync(java.util.Map<String, String> context, boolean sync)
    {
        com.zeroc.IceInternal.OutgoingAsync<Void> f = new com.zeroc.IceInternal.OutgoingAsync<>(this, "volumeUp", null, sync, null);
        f.invoke(false, context, null, null, null);
        return f;
    }

    default void volumeDown()
    {
        volumeDown(com.zeroc.Ice.ObjectPrx.noExplicitContext);
    }

    default void volumeDown(java.util.Map<String, String> context)
    {
        _iceI_volumeDownAsync(context, true).waitForResponse();
    }

    default java.util.concurrent.CompletableFuture<Void> volumeDownAsync()
    {
        return _iceI_volumeDownAsync(com.zeroc.Ice.ObjectPrx.noExplicitContext, false);
    }

    default java.util.concurrent.CompletableFuture<Void> volumeDownAsync(java.util.Map<String, String> context)
    {
        return _iceI_volumeDownAsync(context, false);
    }

    /**
     * @hidden
     * @param context -
     * @param sync -
     * @return -
     **/
    default com.zeroc.IceInternal.OutgoingAsync<Void> _iceI_volumeDownAsync(java.util.Map<String, String> context, boolean sync)
    {
        com.zeroc.IceInternal.OutgoingAsync<Void> f = new com.zeroc.IceInternal.OutgoingAsync<>(this, "volumeDown", null, sync, null);
        f.invoke(false, context, null, null, null);
        return f;
    }

    default String[] getPlaylists()
    {
        return getPlaylists(com.zeroc.Ice.ObjectPrx.noExplicitContext);
    }

    default String[] getPlaylists(java.util.Map<String, String> context)
    {
        return _iceI_getPlaylistsAsync(context, true).waitForResponse();
    }

    default java.util.concurrent.CompletableFuture<String[]> getPlaylistsAsync()
    {
        return _iceI_getPlaylistsAsync(com.zeroc.Ice.ObjectPrx.noExplicitContext, false);
    }

    default java.util.concurrent.CompletableFuture<String[]> getPlaylistsAsync(java.util.Map<String, String> context)
    {
        return _iceI_getPlaylistsAsync(context, false);
    }

    /**
     * @hidden
     * @param context -
     * @param sync -
     * @return -
     **/
    default com.zeroc.IceInternal.OutgoingAsync<String[]> _iceI_getPlaylistsAsync(java.util.Map<String, String> context, boolean sync)
    {
        com.zeroc.IceInternal.OutgoingAsync<String[]> f = new com.zeroc.IceInternal.OutgoingAsync<>(this, "getPlaylists", com.zeroc.Ice.OperationMode.Idempotent, sync, null);
        f.invoke(true, context, null, null, istr -> {
                     String[] ret;
                     ret = istr.readStringSeq();
                     return ret;
                 });
        return f;
    }

    default java.util.Map<java.lang.String, String[]> getMusic()
    {
        return getMusic(com.zeroc.Ice.ObjectPrx.noExplicitContext);
    }

    default java.util.Map<java.lang.String, String[]> getMusic(java.util.Map<String, String> context)
    {
        return _iceI_getMusicAsync(context, true).waitForResponse();
    }

    default java.util.concurrent.CompletableFuture<java.util.Map<java.lang.String, String[]>> getMusicAsync()
    {
        return _iceI_getMusicAsync(com.zeroc.Ice.ObjectPrx.noExplicitContext, false);
    }

    default java.util.concurrent.CompletableFuture<java.util.Map<java.lang.String, String[]>> getMusicAsync(java.util.Map<String, String> context)
    {
        return _iceI_getMusicAsync(context, false);
    }

    /**
     * @hidden
     * @param context -
     * @param sync -
     * @return -
     **/
    default com.zeroc.IceInternal.OutgoingAsync<java.util.Map<java.lang.String, String[]>> _iceI_getMusicAsync(java.util.Map<String, String> context, boolean sync)
    {
        com.zeroc.IceInternal.OutgoingAsync<java.util.Map<java.lang.String, String[]>> f = new com.zeroc.IceInternal.OutgoingAsync<>(this, "getMusic", com.zeroc.Ice.OperationMode.Idempotent, sync, null);
        f.invoke(true, context, null, null, istr -> {
                     java.util.Map<java.lang.String, String[]> ret;
                     ret = musicMapHelper.read(istr);
                     return ret;
                 });
        return f;
    }

    default String[] getSongs(String playlistName)
        throws InvalidPlaylistName
    {
        return getSongs(playlistName, com.zeroc.Ice.ObjectPrx.noExplicitContext);
    }

    default String[] getSongs(String playlistName, java.util.Map<String, String> context)
        throws InvalidPlaylistName
    {
        try
        {
            return _iceI_getSongsAsync(playlistName, context, true).waitForResponseOrUserEx();
        }
        catch(InvalidPlaylistName ex)
        {
            throw ex;
        }
        catch(com.zeroc.Ice.UserException ex)
        {
            throw new com.zeroc.Ice.UnknownUserException(ex.ice_id(), ex);
        }
    }

    default String[] getSongs(java.util.Optional<java.lang.String> playlistName)
        throws InvalidPlaylistName
    {
        return getSongs(playlistName, com.zeroc.Ice.ObjectPrx.noExplicitContext);
    }

    default String[] getSongs(java.util.Optional<java.lang.String> playlistName, java.util.Map<String, String> context)
        throws InvalidPlaylistName
    {
        try
        {
            return _iceI_getSongsAsync(playlistName, context, true).waitForResponseOrUserEx();
        }
        catch(InvalidPlaylistName ex)
        {
            throw ex;
        }
        catch(com.zeroc.Ice.UserException ex)
        {
            throw new com.zeroc.Ice.UnknownUserException(ex.ice_id(), ex);
        }
    }

    default java.util.concurrent.CompletableFuture<String[]> getSongsAsync(String playlistName)
    {
        return _iceI_getSongsAsync(playlistName, com.zeroc.Ice.ObjectPrx.noExplicitContext, false);
    }

    default java.util.concurrent.CompletableFuture<String[]> getSongsAsync(String playlistName, java.util.Map<String, String> context)
    {
        return _iceI_getSongsAsync(playlistName, context, false);
    }

    /**
     * @hidden
     * @param iceP_playlistName -
     * @param context -
     * @param sync -
     * @return -
     **/
    default com.zeroc.IceInternal.OutgoingAsync<String[]> _iceI_getSongsAsync(String iceP_playlistName, java.util.Map<String, String> context, boolean sync)
    {
        com.zeroc.IceInternal.OutgoingAsync<String[]> f = new com.zeroc.IceInternal.OutgoingAsync<>(this, "getSongs", com.zeroc.Ice.OperationMode.Idempotent, sync, _iceE_getSongs);
        f.invoke(true, context, null, ostr -> {
                     ostr.writeString(1, iceP_playlistName);
                 }, istr -> {
                     String[] ret;
                     ret = istr.readStringSeq();
                     return ret;
                 });
        return f;
    }

    /** @hidden */
    static final Class<?>[] _iceE_getSongs =
    {
        InvalidPlaylistName.class
    };

    default java.util.concurrent.CompletableFuture<String[]> getSongsAsync(java.util.Optional<java.lang.String> playlistName)
    {
        return _iceI_getSongsAsync(playlistName, com.zeroc.Ice.ObjectPrx.noExplicitContext, false);
    }

    default java.util.concurrent.CompletableFuture<String[]> getSongsAsync(java.util.Optional<java.lang.String> playlistName, java.util.Map<String, String> context)
    {
        return _iceI_getSongsAsync(playlistName, context, false);
    }

    /**
     * @hidden
     * @param iceP_playlistName -
     * @param context -
     * @param sync -
     * @return -
     **/
    default com.zeroc.IceInternal.OutgoingAsync<String[]> _iceI_getSongsAsync(java.util.Optional<java.lang.String> iceP_playlistName, java.util.Map<String, String> context, boolean sync)
    {
        com.zeroc.IceInternal.OutgoingAsync<String[]> f = new com.zeroc.IceInternal.OutgoingAsync<>(this, "getSongs", com.zeroc.Ice.OperationMode.Idempotent, sync, _iceE_getSongs);
        f.invoke(true, context, null, ostr -> {
                     ostr.writeString(1, iceP_playlistName);
                 }, istr -> {
                     String[] ret;
                     ret = istr.readStringSeq();
                     return ret;
                 });
        return f;
    }

    default void setPlaylist(String playlistName)
        throws InvalidPlaylistName
    {
        setPlaylist(playlistName, com.zeroc.Ice.ObjectPrx.noExplicitContext);
    }

    default void setPlaylist(String playlistName, java.util.Map<String, String> context)
        throws InvalidPlaylistName
    {
        try
        {
            _iceI_setPlaylistAsync(playlistName, context, true).waitForResponseOrUserEx();
        }
        catch(InvalidPlaylistName ex)
        {
            throw ex;
        }
        catch(com.zeroc.Ice.UserException ex)
        {
            throw new com.zeroc.Ice.UnknownUserException(ex.ice_id(), ex);
        }
    }

    default java.util.concurrent.CompletableFuture<Void> setPlaylistAsync(String playlistName)
    {
        return _iceI_setPlaylistAsync(playlistName, com.zeroc.Ice.ObjectPrx.noExplicitContext, false);
    }

    default java.util.concurrent.CompletableFuture<Void> setPlaylistAsync(String playlistName, java.util.Map<String, String> context)
    {
        return _iceI_setPlaylistAsync(playlistName, context, false);
    }

    /**
     * @hidden
     * @param iceP_playlistName -
     * @param context -
     * @param sync -
     * @return -
     **/
    default com.zeroc.IceInternal.OutgoingAsync<Void> _iceI_setPlaylistAsync(String iceP_playlistName, java.util.Map<String, String> context, boolean sync)
    {
        com.zeroc.IceInternal.OutgoingAsync<Void> f = new com.zeroc.IceInternal.OutgoingAsync<>(this, "setPlaylist", com.zeroc.Ice.OperationMode.Idempotent, sync, _iceE_setPlaylist);
        f.invoke(true, context, null, ostr -> {
                     ostr.writeString(iceP_playlistName);
                 }, null);
        return f;
    }

    /** @hidden */
    static final Class<?>[] _iceE_setPlaylist =
    {
        InvalidPlaylistName.class
    };

    default void setSong(String songName)
        throws InvalidSongName
    {
        setSong(songName, com.zeroc.Ice.ObjectPrx.noExplicitContext);
    }

    default void setSong(String songName, java.util.Map<String, String> context)
        throws InvalidSongName
    {
        try
        {
            _iceI_setSongAsync(songName, context, true).waitForResponseOrUserEx();
        }
        catch(InvalidSongName ex)
        {
            throw ex;
        }
        catch(com.zeroc.Ice.UserException ex)
        {
            throw new com.zeroc.Ice.UnknownUserException(ex.ice_id(), ex);
        }
    }

    default java.util.concurrent.CompletableFuture<Void> setSongAsync(String songName)
    {
        return _iceI_setSongAsync(songName, com.zeroc.Ice.ObjectPrx.noExplicitContext, false);
    }

    default java.util.concurrent.CompletableFuture<Void> setSongAsync(String songName, java.util.Map<String, String> context)
    {
        return _iceI_setSongAsync(songName, context, false);
    }

    /**
     * @hidden
     * @param iceP_songName -
     * @param context -
     * @param sync -
     * @return -
     **/
    default com.zeroc.IceInternal.OutgoingAsync<Void> _iceI_setSongAsync(String iceP_songName, java.util.Map<String, String> context, boolean sync)
    {
        com.zeroc.IceInternal.OutgoingAsync<Void> f = new com.zeroc.IceInternal.OutgoingAsync<>(this, "setSong", com.zeroc.Ice.OperationMode.Idempotent, sync, _iceE_setSong);
        f.invoke(true, context, null, ostr -> {
                     ostr.writeString(iceP_songName);
                 }, null);
        return f;
    }

    /** @hidden */
    static final Class<?>[] _iceE_setSong =
    {
        InvalidSongName.class
    };

    default void nextSong()
    {
        nextSong(com.zeroc.Ice.ObjectPrx.noExplicitContext);
    }

    default void nextSong(java.util.Map<String, String> context)
    {
        _iceI_nextSongAsync(context, true).waitForResponse();
    }

    default java.util.concurrent.CompletableFuture<Void> nextSongAsync()
    {
        return _iceI_nextSongAsync(com.zeroc.Ice.ObjectPrx.noExplicitContext, false);
    }

    default java.util.concurrent.CompletableFuture<Void> nextSongAsync(java.util.Map<String, String> context)
    {
        return _iceI_nextSongAsync(context, false);
    }

    /**
     * @hidden
     * @param context -
     * @param sync -
     * @return -
     **/
    default com.zeroc.IceInternal.OutgoingAsync<Void> _iceI_nextSongAsync(java.util.Map<String, String> context, boolean sync)
    {
        com.zeroc.IceInternal.OutgoingAsync<Void> f = new com.zeroc.IceInternal.OutgoingAsync<>(this, "nextSong", null, sync, null);
        f.invoke(false, context, null, null, null);
        return f;
    }

    default void prevSong()
    {
        prevSong(com.zeroc.Ice.ObjectPrx.noExplicitContext);
    }

    default void prevSong(java.util.Map<String, String> context)
    {
        _iceI_prevSongAsync(context, true).waitForResponse();
    }

    default java.util.concurrent.CompletableFuture<Void> prevSongAsync()
    {
        return _iceI_prevSongAsync(com.zeroc.Ice.ObjectPrx.noExplicitContext, false);
    }

    default java.util.concurrent.CompletableFuture<Void> prevSongAsync(java.util.Map<String, String> context)
    {
        return _iceI_prevSongAsync(context, false);
    }

    /**
     * @hidden
     * @param context -
     * @param sync -
     * @return -
     **/
    default com.zeroc.IceInternal.OutgoingAsync<Void> _iceI_prevSongAsync(java.util.Map<String, String> context, boolean sync)
    {
        com.zeroc.IceInternal.OutgoingAsync<Void> f = new com.zeroc.IceInternal.OutgoingAsync<>(this, "prevSong", null, sync, null);
        f.invoke(false, context, null, null, null);
        return f;
    }

    default void startStop()
    {
        startStop(com.zeroc.Ice.ObjectPrx.noExplicitContext);
    }

    default void startStop(java.util.Map<String, String> context)
    {
        _iceI_startStopAsync(context, true).waitForResponse();
    }

    default java.util.concurrent.CompletableFuture<Void> startStopAsync()
    {
        return _iceI_startStopAsync(com.zeroc.Ice.ObjectPrx.noExplicitContext, false);
    }

    default java.util.concurrent.CompletableFuture<Void> startStopAsync(java.util.Map<String, String> context)
    {
        return _iceI_startStopAsync(context, false);
    }

    /**
     * @hidden
     * @param context -
     * @param sync -
     * @return -
     **/
    default com.zeroc.IceInternal.OutgoingAsync<Void> _iceI_startStopAsync(java.util.Map<String, String> context, boolean sync)
    {
        com.zeroc.IceInternal.OutgoingAsync<Void> f = new com.zeroc.IceInternal.OutgoingAsync<>(this, "startStop", null, sync, null);
        f.invoke(false, context, null, null, null);
        return f;
    }

    /**
     * Contacts the remote server to verify that the object implements this type.
     * Raises a local exception if a communication error occurs.
     * @param obj The untyped proxy.
     * @return A proxy for this type, or null if the object does not support this type.
     **/
    static MusicPrx checkedCast(com.zeroc.Ice.ObjectPrx obj)
    {
        return com.zeroc.Ice.ObjectPrx._checkedCast(obj, ice_staticId(), MusicPrx.class, _MusicPrxI.class);
    }

    /**
     * Contacts the remote server to verify that the object implements this type.
     * Raises a local exception if a communication error occurs.
     * @param obj The untyped proxy.
     * @param context The Context map to send with the invocation.
     * @return A proxy for this type, or null if the object does not support this type.
     **/
    static MusicPrx checkedCast(com.zeroc.Ice.ObjectPrx obj, java.util.Map<String, String> context)
    {
        return com.zeroc.Ice.ObjectPrx._checkedCast(obj, context, ice_staticId(), MusicPrx.class, _MusicPrxI.class);
    }

    /**
     * Contacts the remote server to verify that a facet of the object implements this type.
     * Raises a local exception if a communication error occurs.
     * @param obj The untyped proxy.
     * @param facet The name of the desired facet.
     * @return A proxy for this type, or null if the object does not support this type.
     **/
    static MusicPrx checkedCast(com.zeroc.Ice.ObjectPrx obj, String facet)
    {
        return com.zeroc.Ice.ObjectPrx._checkedCast(obj, facet, ice_staticId(), MusicPrx.class, _MusicPrxI.class);
    }

    /**
     * Contacts the remote server to verify that a facet of the object implements this type.
     * Raises a local exception if a communication error occurs.
     * @param obj The untyped proxy.
     * @param facet The name of the desired facet.
     * @param context The Context map to send with the invocation.
     * @return A proxy for this type, or null if the object does not support this type.
     **/
    static MusicPrx checkedCast(com.zeroc.Ice.ObjectPrx obj, String facet, java.util.Map<String, String> context)
    {
        return com.zeroc.Ice.ObjectPrx._checkedCast(obj, facet, context, ice_staticId(), MusicPrx.class, _MusicPrxI.class);
    }

    /**
     * Downcasts the given proxy to this type without contacting the remote server.
     * @param obj The untyped proxy.
     * @return A proxy for this type.
     **/
    static MusicPrx uncheckedCast(com.zeroc.Ice.ObjectPrx obj)
    {
        return com.zeroc.Ice.ObjectPrx._uncheckedCast(obj, MusicPrx.class, _MusicPrxI.class);
    }

    /**
     * Downcasts the given proxy to this type without contacting the remote server.
     * @param obj The untyped proxy.
     * @param facet The name of the desired facet.
     * @return A proxy for this type.
     **/
    static MusicPrx uncheckedCast(com.zeroc.Ice.ObjectPrx obj, String facet)
    {
        return com.zeroc.Ice.ObjectPrx._uncheckedCast(obj, facet, MusicPrx.class, _MusicPrxI.class);
    }

    /**
     * Returns a proxy that is identical to this proxy, except for the per-proxy context.
     * @param newContext The context for the new proxy.
     * @return A proxy with the specified per-proxy context.
     **/
    @Override
    default MusicPrx ice_context(java.util.Map<String, String> newContext)
    {
        return (MusicPrx)_ice_context(newContext);
    }

    /**
     * Returns a proxy that is identical to this proxy, except for the adapter ID.
     * @param newAdapterId The adapter ID for the new proxy.
     * @return A proxy with the specified adapter ID.
     **/
    @Override
    default MusicPrx ice_adapterId(String newAdapterId)
    {
        return (MusicPrx)_ice_adapterId(newAdapterId);
    }

    /**
     * Returns a proxy that is identical to this proxy, except for the endpoints.
     * @param newEndpoints The endpoints for the new proxy.
     * @return A proxy with the specified endpoints.
     **/
    @Override
    default MusicPrx ice_endpoints(com.zeroc.Ice.Endpoint[] newEndpoints)
    {
        return (MusicPrx)_ice_endpoints(newEndpoints);
    }

    /**
     * Returns a proxy that is identical to this proxy, except for the locator cache timeout.
     * @param newTimeout The new locator cache timeout (in seconds).
     * @return A proxy with the specified locator cache timeout.
     **/
    @Override
    default MusicPrx ice_locatorCacheTimeout(int newTimeout)
    {
        return (MusicPrx)_ice_locatorCacheTimeout(newTimeout);
    }

    /**
     * Returns a proxy that is identical to this proxy, except for the invocation timeout.
     * @param newTimeout The new invocation timeout (in seconds).
     * @return A proxy with the specified invocation timeout.
     **/
    @Override
    default MusicPrx ice_invocationTimeout(int newTimeout)
    {
        return (MusicPrx)_ice_invocationTimeout(newTimeout);
    }

    /**
     * Returns a proxy that is identical to this proxy, except for connection caching.
     * @param newCache <code>true</code> if the new proxy should cache connections; <code>false</code> otherwise.
     * @return A proxy with the specified caching policy.
     **/
    @Override
    default MusicPrx ice_connectionCached(boolean newCache)
    {
        return (MusicPrx)_ice_connectionCached(newCache);
    }

    /**
     * Returns a proxy that is identical to this proxy, except for the endpoint selection policy.
     * @param newType The new endpoint selection policy.
     * @return A proxy with the specified endpoint selection policy.
     **/
    @Override
    default MusicPrx ice_endpointSelection(com.zeroc.Ice.EndpointSelectionType newType)
    {
        return (MusicPrx)_ice_endpointSelection(newType);
    }

    /**
     * Returns a proxy that is identical to this proxy, except for how it selects endpoints.
     * @param b If <code>b</code> is <code>true</code>, only endpoints that use a secure transport are
     * used by the new proxy. If <code>b</code> is false, the returned proxy uses both secure and
     * insecure endpoints.
     * @return A proxy with the specified selection policy.
     **/
    @Override
    default MusicPrx ice_secure(boolean b)
    {
        return (MusicPrx)_ice_secure(b);
    }

    /**
     * Returns a proxy that is identical to this proxy, except for the encoding used to marshal parameters.
     * @param e The encoding version to use to marshal request parameters.
     * @return A proxy with the specified encoding version.
     **/
    @Override
    default MusicPrx ice_encodingVersion(com.zeroc.Ice.EncodingVersion e)
    {
        return (MusicPrx)_ice_encodingVersion(e);
    }

    /**
     * Returns a proxy that is identical to this proxy, except for its endpoint selection policy.
     * @param b If <code>b</code> is <code>true</code>, the new proxy will use secure endpoints for invocations
     * and only use insecure endpoints if an invocation cannot be made via secure endpoints. If <code>b</code> is
     * <code>false</code>, the proxy prefers insecure endpoints to secure ones.
     * @return A proxy with the specified selection policy.
     **/
    @Override
    default MusicPrx ice_preferSecure(boolean b)
    {
        return (MusicPrx)_ice_preferSecure(b);
    }

    /**
     * Returns a proxy that is identical to this proxy, except for the router.
     * @param router The router for the new proxy.
     * @return A proxy with the specified router.
     **/
    @Override
    default MusicPrx ice_router(com.zeroc.Ice.RouterPrx router)
    {
        return (MusicPrx)_ice_router(router);
    }

    /**
     * Returns a proxy that is identical to this proxy, except for the locator.
     * @param locator The locator for the new proxy.
     * @return A proxy with the specified locator.
     **/
    @Override
    default MusicPrx ice_locator(com.zeroc.Ice.LocatorPrx locator)
    {
        return (MusicPrx)_ice_locator(locator);
    }

    /**
     * Returns a proxy that is identical to this proxy, except for collocation optimization.
     * @param b <code>true</code> if the new proxy enables collocation optimization; <code>false</code> otherwise.
     * @return A proxy with the specified collocation optimization.
     **/
    @Override
    default MusicPrx ice_collocationOptimized(boolean b)
    {
        return (MusicPrx)_ice_collocationOptimized(b);
    }

    /**
     * Returns a proxy that is identical to this proxy, but uses twoway invocations.
     * @return A proxy that uses twoway invocations.
     **/
    @Override
    default MusicPrx ice_twoway()
    {
        return (MusicPrx)_ice_twoway();
    }

    /**
     * Returns a proxy that is identical to this proxy, but uses oneway invocations.
     * @return A proxy that uses oneway invocations.
     **/
    @Override
    default MusicPrx ice_oneway()
    {
        return (MusicPrx)_ice_oneway();
    }

    /**
     * Returns a proxy that is identical to this proxy, but uses batch oneway invocations.
     * @return A proxy that uses batch oneway invocations.
     **/
    @Override
    default MusicPrx ice_batchOneway()
    {
        return (MusicPrx)_ice_batchOneway();
    }

    /**
     * Returns a proxy that is identical to this proxy, but uses datagram invocations.
     * @return A proxy that uses datagram invocations.
     **/
    @Override
    default MusicPrx ice_datagram()
    {
        return (MusicPrx)_ice_datagram();
    }

    /**
     * Returns a proxy that is identical to this proxy, but uses batch datagram invocations.
     * @return A proxy that uses batch datagram invocations.
     **/
    @Override
    default MusicPrx ice_batchDatagram()
    {
        return (MusicPrx)_ice_batchDatagram();
    }

    /**
     * Returns a proxy that is identical to this proxy, except for compression.
     * @param co <code>true</code> enables compression for the new proxy; <code>false</code> disables compression.
     * @return A proxy with the specified compression setting.
     **/
    @Override
    default MusicPrx ice_compress(boolean co)
    {
        return (MusicPrx)_ice_compress(co);
    }

    /**
     * Returns a proxy that is identical to this proxy, except for its connection timeout setting.
     * @param t The connection timeout for the proxy in milliseconds.
     * @return A proxy with the specified timeout.
     **/
    @Override
    default MusicPrx ice_timeout(int t)
    {
        return (MusicPrx)_ice_timeout(t);
    }

    /**
     * Returns a proxy that is identical to this proxy, except for its connection ID.
     * @param connectionId The connection ID for the new proxy. An empty string removes the connection ID.
     * @return A proxy with the specified connection ID.
     **/
    @Override
    default MusicPrx ice_connectionId(String connectionId)
    {
        return (MusicPrx)_ice_connectionId(connectionId);
    }

    /**
     * Returns a proxy that is identical to this proxy, except it's a fixed proxy bound
     * the given connection.@param connection The fixed proxy connection.
     * @return A fixed proxy bound to the given connection.
     **/
    @Override
    default MusicPrx ice_fixed(com.zeroc.Ice.Connection connection)
    {
        return (MusicPrx)_ice_fixed(connection);
    }

    static String ice_staticId()
    {
        return "::Home::Audio::Music";
    }
}