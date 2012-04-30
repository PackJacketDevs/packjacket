/*
 * PackJacket - GUI frontend to IzPack to make Java-based installers
 * Copyright (C) 2008 - 2009  Amandeep Grewal, Manodasan Wignarajah
 *
 * PackJacket is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * PackJacket is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with PackJacket.  If not, see <http://www.gnu.org/licenses/>.
 */
package packjacket.gui;

import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.Transferable;
import java.awt.datatransfer.UnsupportedFlavorException;
import java.io.IOException;
import javax.swing.DefaultListModel;
import javax.swing.Icon;
import javax.swing.JComponent;
import javax.swing.JList;
import javax.swing.ListSelectionModel;
import javax.swing.SwingUtilities;
import javax.swing.TransferHandler;
import javax.swing.TransferHandler.TransferSupport;

/**
 * A draggable JList
 * @author Saeven
 * @see http://forums.crm.saeven.net/blog.php?b=2
 */
public class DList extends JList {

    private static final long serialVersionUID = 1L;
    public static DataFlavor DList_Flavor = new DataFlavor(DListData.class, "DListData");
    private static DataFlavor[] supportedFlavors = {DList_Flavor};

    public DList() {
        super();
        setTransferHandler(new ReorderHandler());
        setDragEnabled(true);
        setSelectionMode(ListSelectionModel.SINGLE_INTERVAL_SELECTION);
    }

    public DList(DefaultListModel m) {
        this();
        setModel(m);
    }

    public void dropComplete() {
    }

    private class ReorderHandler extends TransferHandler {

        private static final long serialVersionUID = 1L;

        @Override
        public boolean importData(TransferSupport support) {

            // this is the index of the element onto which the dragged element, is dropped
            final int dropIndex = DList.this.locationToIndex(getDropLocation().getDropPoint());

            try {
                Object[] draggedData = ((DListData) support.getTransferable().getTransferData(DList_Flavor)).data;
                final DList dragList = ((DListData) support.getTransferable().getTransferData(DList_Flavor)).parent;
                DefaultListModel dragModel = (DefaultListModel) dragList.getModel();
                DefaultListModel dropModel = (DefaultListModel) DList.this.getModel();

                final Object leadItem = dropIndex >= 0 ? dropModel.elementAt(dropIndex) : null;
                final int dataLength = draggedData.length;

                // make sure that the lead item, is not in the dragged data
                if (leadItem != null) {
                    for (int i = 0; i < dataLength; i++) {
                        if (draggedData[i].equals(leadItem)) {
                            return false;
                        }
                    }
                }

                int dragLeadIndex = -1;
                final boolean localDrop = dropModel.contains(draggedData[0]);

                if (localDrop) {
                    dragLeadIndex = dropModel.indexOf(draggedData[0]);
                }

                for (int i = 0; i < dataLength; i++) {
                    dragModel.removeElement(draggedData[i]);
                }

                if (localDrop) {
                    final int adjustedLeadIndex = dropModel.indexOf(leadItem);
                    final int insertionAdjustment = dragLeadIndex <= adjustedLeadIndex ? 1 : 0;

                    final int[] indices = new int[dataLength];
                    for (int i = 0; i < dataLength; i++) {
                        dropModel.insertElementAt(draggedData[i], adjustedLeadIndex + insertionAdjustment + i);
                        indices[i] = adjustedLeadIndex + insertionAdjustment + i;
                    }

                    SwingUtilities.invokeLater(new Runnable() {

                        @Override
                        public void run() {
                            DList.this.clearSelection();
                            DList.this.setSelectedIndices(indices);
                            dropComplete();
                        }
                    });
                } else {
                    final int[] indices = new int[dataLength];
                    for (int i = 0; i < dataLength; i++) {
                        dropModel.insertElementAt(draggedData[i], dropIndex + 1);
                        indices[i] = dropIndex + 1 + i;
                    }

                    SwingUtilities.invokeLater(new Runnable() {

                        @Override
                        public void run() {
                            DList.this.clearSelection();
                            DList.this.setSelectedIndices(indices);
                            dragList.clearSelection();
                            dropComplete();
                        }
                    });
                }
            } catch (Exception x) {
                x.printStackTrace();
            }
            return false;
        }

        @Override
        public int getSourceActions(JComponent c) {
            return TransferHandler.MOVE;
        }

        @Override
        protected Transferable createTransferable(JComponent c) {
            return new DListData(DList.this, DList.this.getSelectedValues());
        }

        @Override
        public boolean canImport(TransferSupport support) {
            if (!support.isDrop() || !support.isDataFlavorSupported(DList_Flavor)) {
                return false;
            }


            return true;
        }

        @Override
        public Icon getVisualRepresentation(Transferable t) {
            return super.getVisualRepresentation(t);
        }
    }

    private class DListData implements Transferable {

        private Object[] data;
        private DList parent;

        protected DListData(DList p, Object[] d) {
            parent = p;
            data = d;
        }

        @Override
        public Object getTransferData(DataFlavor flavor)
                throws UnsupportedFlavorException, IOException {
            if (flavor.equals(DList_Flavor)) {
                return DListData.this;
            } else {
                return null;
            }
        }

        @Override
        public DataFlavor[] getTransferDataFlavors() {
            return supportedFlavors;
        }

        @Override
        public boolean isDataFlavorSupported(DataFlavor flavor) {
            return true;
        }
    }
}
