import { useState } from "react";
import { Button } from "./ui/button";
import { Input } from "./ui/input";
import { Label } from "./ui/label";
import {
  Select,
  SelectContent,
  SelectItem,
  SelectTrigger,
  SelectValue,
} from "./ui/select";
import { Card } from "./ui/card";
import {
  Trash2,
  User,
  Server,
  Database,
  Globe,
  Smartphone,
  Monitor,
  Cloud,
  Code,
  Mail,
  ShoppingCart,
  CreditCard,
  FileText,
  Settings,
  Edit2,
  Check,
  X,
  Plus,
  ArrowUp,
  ArrowDown,
} from "lucide-react";
import type { Participant } from "./SequenceDiagram";
import { useLanguage } from "../contexts/LanguageContext";
import { getTranslation } from "../../utils/translations";

interface ParticipantEditorProps {
  participants: Participant[];
  onAdd: (name: string, icon?: string) => void;
  onRemove: (id: string) => void;
  onUpdateIcon: (id: string, icon?: string) => void;
  onUpdateName: (id: string, name: string) => void;
  onReorder: (newParticipants: Participant[]) => void;
}

export function ParticipantEditor({
  participants,
  onAdd,
  onRemove,
  onUpdateIcon,
  onUpdateName,
  onReorder,
}: ParticipantEditorProps) {
  const { language } = useLanguage();
  const t = (key: any) => getTranslation(language, key);

  const iconOptions = [
    { value: "user", label: t("iconUser"), Icon: User },
    { value: "server", label: t("iconServer"), Icon: Server },
    { value: "database", label: t("iconDatabase"), Icon: Database },
    { value: "globe", label: t("iconGlobe"), Icon: Globe },
    { value: "smartphone", label: t("iconSmartphone"), Icon: Smartphone },
    { value: "monitor", label: t("iconMonitor"), Icon: Monitor },
    { value: "cloud", label: t("iconCloud"), Icon: Cloud },
    { value: "code", label: t("iconCode"), Icon: Code },
    { value: "mail", label: t("iconMail"), Icon: Mail },
    {
      value: "shopping-cart",
      label: t("iconShoppingCart"),
      Icon: ShoppingCart,
    },
    { value: "credit-card", label: t("iconCreditCard"), Icon: CreditCard },
    { value: "file-text", label: t("iconFileText"), Icon: FileText },
    { value: "settings", label: t("iconSettings"), Icon: Settings },
  ];
  const [newName, setNewName] = useState("");
  const [newIcon, setNewIcon] = useState<string>("");
  const [editingId, setEditingId] = useState<string | null>(
    null,
  );
  const [editingName, setEditingName] = useState("");

  const handleAdd = () => {
    if (newName.trim()) {
      onAdd(newName.trim(), newIcon || undefined);
      setNewName("");
      setNewIcon("");
    }
  };

  const handleKeyPress = (e: React.KeyboardEvent) => {
    if (e.key === "Enter") handleAdd();
  };

  const startEditing = (participant: Participant) => {
    setEditingId(participant.id);
    setEditingName(participant.name);
  };

  const cancelEditing = () => {
    setEditingId(null);
    setEditingName("");
  };

  const saveEditing = (id: string) => {
    if (editingName.trim()) {
      onUpdateName(id, editingName.trim());
      setEditingId(null);
      setEditingName("");
    }
  };

  const handleMove = (id: string, direction: "up" | "down") => {
    const index = participants.findIndex((p) => p.id === id);

    if (!onReorder || typeof onReorder !== "function") {
      console.error(
        "Error: onReorder function not provided from parent!",
      );
      alert(
        "Error: Cannot change order. Please refresh the page.",
      );
      return;
    }

    if (index === -1) return;
    if (direction === "up" && index === 0) return;
    if (
      direction === "down" &&
      index === participants.length - 1
    )
      return;

    const newParticipants = [...participants];
    const targetIndex =
      direction === "up" ? index - 1 : index + 1;
    [newParticipants[index], newParticipants[targetIndex]] = [
      newParticipants[targetIndex],
      newParticipants[index],
    ];

    onReorder(newParticipants);
  };

  const getIconComponent = (iconValue?: string) => {
    return iconOptions.find((opt) => opt.value === iconValue)
      ?.Icon;
  };

  return (
    <Card className="p-4">
      <h3 className="font-semibold mb-4 text-slate-900">
        {t('participantManage')}
      </h3>

      {/* 리스트 영역 (MessageEditor 스타일) */}
      <div className="space-y-2 mb-6 max-h-[300px] overflow-y-auto">
        {participants.map((participant, index) => {
          const IconComponent = getIconComponent(
            participant.icon,
          );
          return (
            <div
              key={participant.id}
              className="flex items-start gap-2 text-sm"
            >
              <div className="flex-1 bg-slate-50 rounded-lg border border-slate-200 p-3">
                <div className="flex items-center gap-2 mb-2">
                  {IconComponent && (
                    <IconComponent className="h-4 w-4 text-blue-600" />
                  )}
                  {editingId === participant.id ? (
                    <Input
                      value={editingName}
                      onChange={(e) =>
                        setEditingName(e.target.value)
                      }
                      onKeyDown={(e) =>
                        e.key === "Enter"
                          ? saveEditing(participant.id)
                          : e.key === "Escape" &&
                            cancelEditing()
                      }
                      className="h-7 text-sm"
                      autoFocus
                    />
                  ) : (
                    <span className="font-medium text-slate-900">
                      {participant.name}
                    </span>
                  )}
                </div>

                <div className="flex flex-wrap gap-1 mt-2">
                  <Button
                    variant="ghost"
                    size="sm"
                    onClick={() =>
                      handleMove(participant.id, "up")
                    }
                    disabled={index === 0}
                    className="h-7 px-2 text-xs"
                  >
                    <ArrowUp className="h-3 w-3 mr-1" />
                    {t('moveUp')}
                  </Button>
                  <Button
                    variant="ghost"
                    size="sm"
                    onClick={() =>
                      handleMove(participant.id, "down")
                    }
                    disabled={index === participants.length - 1}
                    className="h-7 px-2 text-xs"
                  >
                    <ArrowDown className="h-3 w-3 mr-1" />
                    {t('moveDown')}
                  </Button>

                  {editingId === participant.id ? (
                    <>
                      <Button
                        variant="ghost"
                        size="sm"
                        onClick={() =>
                          saveEditing(participant.id)
                        }
                        className="h-7 px-2 text-xs text-green-600"
                      >
                        <Check className="h-3 w-3 mr-1" /> {t('save')}
                      </Button>
                      <Button
                        variant="ghost"
                        size="sm"
                        onClick={cancelEditing}
                        className="h-7 px-2 text-xs text-slate-600"
                      >
                        <X className="h-3 w-3 mr-1" /> {t('cancel')}
                      </Button>
                    </>
                  ) : (
                    <>
                      <Button
                        variant="ghost"
                        size="sm"
                        onClick={() =>
                          startEditing(participant)
                        }
                        className="h-7 px-2 text-xs text-blue-600"
                      >
                        <Edit2 className="h-3 w-3 mr-1" /> {t('edit')}
                      </Button>
                      <Select
                        value={participant.icon || "none"}
                        onValueChange={(value) =>
                          onUpdateIcon(
                            participant.id,
                            value === "none"
                              ? undefined
                              : value,
                          )
                        }
                      >
                        <SelectTrigger className="h-7 w-[110px] text-[10px] bg-transparent border-none hover:bg-slate-100">
                          <SelectValue placeholder={t('iconChange')} />
                        </SelectTrigger>
                        <SelectContent>
                          <SelectItem value="none">
                            {t('iconNone')}
                          </SelectItem>
                          {iconOptions.map(
                            ({ value, label }) => (
                              <SelectItem
                                key={value}
                                value={value}
                              >
                                {label}
                              </SelectItem>
                            ),
                          )}
                        </SelectContent>
                      </Select>
                      <Button
                        variant="ghost"
                        size="sm"
                        onClick={() => onRemove(participant.id)}
                        className="h-7 px-2 text-xs text-red-600"
                      >
                        <Trash2 className="h-3 w-3 mr-1" /> {t('delete')}
                      </Button>
                    </>
                  )}
                </div>
              </div>
              {/* 우측 순서 번호 표시 */}
              <div className="flex items-center justify-center min-w-[32px] h-8 rounded-full bg-slate-200 text-slate-600 text-sm font-semibold">
                {index + 1}
              </div>
            </div>
          );
        })}
      </div>

      {/* 추가 입력 영역 */}
      <div className="border-t pt-4 space-y-3">
        <div>
          <Label htmlFor="participant-name">{t('participantName')}</Label>
          <Input
            id="participant-name"
            value={newName}
            onChange={(e) => setNewName(e.target.value)}
            onKeyPress={handleKeyPress}
            placeholder={t('participantNamePlaceholder')}
          />
        </div>
        <div>
          <Label htmlFor="participant-icon">
            {t('participantIcon')}
          </Label>
          <Select
            value={newIcon || "none"}
            onValueChange={(v) =>
              setNewIcon(v === "none" ? "" : v)
            }
          >
            <SelectTrigger id="participant-icon">
              <SelectValue placeholder={t('iconSelect')} />
            </SelectTrigger>
            <SelectContent>
              <SelectItem value="none">{t('iconNone')}</SelectItem>
              {iconOptions.map(({ value, label, Icon }) => (
                <SelectItem key={value} value={value}>
                  <div className="flex items-center gap-2">
                    <Icon className="h-4 w-4" /> {label}
                  </div>
                </SelectItem>
              ))}
            </SelectContent>
          </Select>
        </div>
        <Button onClick={handleAdd} className="w-full">
          <Plus className="h-4 w-4 mr-1" /> {t('addParticipant')}
        </Button>
      </div>
    </Card>
  );
}