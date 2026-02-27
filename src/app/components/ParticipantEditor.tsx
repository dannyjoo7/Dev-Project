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

interface ParticipantEditorProps {
  participants: Participant[];
  onAdd: (name: string, icon?: string) => void;
  onRemove: (id: string) => void;
  onUpdateIcon: (id: string, icon?: string) => void;
  onUpdateName: (id: string, name: string) => void;
  onReorder: (newParticipants: Participant[]) => void;
}

const iconOptions = [
  { value: "user", label: "사용자", Icon: User },
  { value: "server", label: "서버", Icon: Server },
  { value: "database", label: "데이터베이스", Icon: Database },
  { value: "globe", label: "웹", Icon: Globe },
  { value: "smartphone", label: "모바일", Icon: Smartphone },
  { value: "monitor", label: "데스크톱", Icon: Monitor },
  { value: "cloud", label: "클라우드", Icon: Cloud },
  { value: "code", label: "코드", Icon: Code },
  { value: "mail", label: "이메일", Icon: Mail },
  {
    value: "shopping-cart",
    label: "장바구니",
    Icon: ShoppingCart,
  },
  { value: "credit-card", label: "결제", Icon: CreditCard },
  { value: "file-text", label: "문서", Icon: FileText },
  { value: "settings", label: "설정", Icon: Settings },
];

export function ParticipantEditor({
  participants,
  onAdd,
  onRemove,
  onUpdateIcon,
  onUpdateName,
  onReorder,
}: ParticipantEditorProps) {
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
        "🚨 심각: 부모로부터 onReorder 함수가 전달되지 않았어!",
      );
      alert(
        "오류: 순서를 변경할 수 없습니다. 화면을 새로고침 해주세요.",
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
        참여자 관리
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
                    위로
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
                    아래로
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
                        <Check className="h-3 w-3 mr-1" /> 저장
                      </Button>
                      <Button
                        variant="ghost"
                        size="sm"
                        onClick={cancelEditing}
                        className="h-7 px-2 text-xs text-slate-600"
                      >
                        <X className="h-3 w-3 mr-1" /> 취소
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
                        <Edit2 className="h-3 w-3 mr-1" /> 편집
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
                          <SelectValue placeholder="아이콘 변경" />
                        </SelectTrigger>
                        <SelectContent>
                          <SelectItem value="none">
                            아이콘 없음
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
                        <Trash2 className="h-3 w-3 mr-1" /> 삭제
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
          <Label htmlFor="participant-name">이름</Label>
          <Input
            id="participant-name"
            value={newName}
            onChange={(e) => setNewName(e.target.value)}
            onKeyPress={handleKeyPress}
            placeholder="참여자 이름"
          />
        </div>
        <div>
          <Label htmlFor="participant-icon">
            아이콘 (선택)
          </Label>
          <Select
            value={newIcon || "none"}
            onValueChange={(v) =>
              setNewIcon(v === "none" ? "" : v)
            }
          >
            <SelectTrigger id="participant-icon">
              <SelectValue placeholder="아이콘 선택" />
            </SelectTrigger>
            <SelectContent>
              <SelectItem value="none">아이콘 없음</SelectItem>
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
          <Plus className="h-4 w-4 mr-1" /> 참여자 추가
        </Button>
      </div>
    </Card>
  );
}