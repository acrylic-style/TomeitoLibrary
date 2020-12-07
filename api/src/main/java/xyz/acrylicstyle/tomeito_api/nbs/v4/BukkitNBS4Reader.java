package xyz.acrylicstyle.tomeito_api.nbs.v4;

import org.jetbrains.annotations.NotNull;
import util.nbs.NBSHeader;
import util.nbs.NBSInstrument;
import util.nbs.NBSLayerData;
import util.nbs.v4.NBS4Reader;
import xyz.acrylicstyle.tomeito_api.nbs.BukkitNBSFile;
import xyz.acrylicstyle.tomeito_api.nbs.BukkitNBSNote;
import xyz.acrylicstyle.tomeito_api.nbs.BukkitNBSReader;
import xyz.acrylicstyle.tomeito_api.nbs.BukkitNBSTick;

import java.io.File;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class BukkitNBS4Reader extends NBS4Reader implements BukkitNBSReader {
    @Override
    public @NotNull BukkitNBSFile read(@NotNull File file) throws IOException {
        return (BukkitNBSFile) super.read(file);
    }

    @Override
    public @NotNull BukkitNBS4File readBody(@NotNull NBSHeader header, @NotNull ByteBuffer buffer) {
        List<BukkitNBSTick> ticks = readBukkitNotes(header.getLayers(), buffer);
        List<NBSLayerData> layers = Arrays.asList(readDetailedLayerDataEntries(header.getLayers(), buffer));
        List<NBSInstrument> customInstruments = Arrays.asList(readInstrumentDataEntries(buffer));
        return new BukkitNBS4File(header, ticks, layers, customInstruments);
    }

    @Override
    public @NotNull ArrayList<BukkitNBSTick> readBukkitNotes(int layers, @NotNull ByteBuffer buffer) {
        int currentTick = -1;
        ArrayList<BukkitNBSTick> ticks = new ArrayList<>();
        while (true) {
            int tickJumps = readUnsignedShort(buffer);
            if (tickJumps == 0) break;
            currentTick += tickJumps;
            List<BukkitNBSNote> notes = Arrays.asList(readTickNoteLayers(layers, buffer));
            ticks.add(new BukkitNBS4Tick(currentTick, notes));
        }
        return ticks;
    }

    @Override
    public BukkitNBSNote[] readTickNoteLayers(int layers, ByteBuffer buffer) {
        BukkitNBSNote[] notes = new BukkitNBSNote[layers];
        int currentLayer = -1;
        while (true) {
            int layerJumps = readUnsignedShort(buffer);
            if (layerJumps == 0) break;
            currentLayer += layerJumps;
            BukkitNBSNote note = readNote(buffer);
            notes[currentLayer] = note;
        }
        return notes;
    }

    @Override
    public BukkitNBSNote readNote(ByteBuffer buffer) {
        byte instrument = buffer.get();
        byte key = buffer.get();
        byte volume = buffer.get();
        byte panning = buffer.get();
        short pitch = buffer.getShort();
        return new BukkitNBS4Note(instrument, key, volume, panning, pitch);
    }
}